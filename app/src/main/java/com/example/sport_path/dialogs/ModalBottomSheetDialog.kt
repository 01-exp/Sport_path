package com.example.sport_path.dialogs

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Place
import com.example.sport_path.databinding.FragmentModalBottomSheetBinding
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.maps.PlaceOnlineAdapter
import com.example.sport_path.services.maps.PlacesViewModel
import com.example.sport_path.services.maps.PlacesViewModelFactory
import com.example.sport_path.services.users.UserManager
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.WifiChecker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar

class ModalBottomSheetDialog(private var place: Place,var entriesList: MutableList<Entry>) : BottomSheetDialogFragment(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.onClickListener {
    lateinit var binding: FragmentModalBottomSheetBinding
    lateinit var date: String
    lateinit var time: String
    lateinit var timePickerDialog: TimePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentModalBottomSheetBinding.inflate(layoutInflater)
        binding.nameTextView.text = Utils.cutAddress(place.address)

        val placesViewModel = ViewModelProvider(
            this,
            PlacesViewModelFactory()
        )[PlacesViewModel::class.java]

        placesViewModel.placeOnlineList.observe(this) {
            showPlaceOnlineDialog(it)
        }


        binding.doEntryButton.setOnClickListener {
            if (WifiChecker.isInternetConnected(requireContext())) {
                val calendar = Calendar.getInstance()
                showDatePickerDialog(calendar)
            } else {
                Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
            }
        }
        binding.onlineButton.setOnClickListener {
            if (WifiChecker.isInternetConnected(requireContext())) {
                placesViewModel.loadPlaceOnline(place.id)
            } else {
                Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun showPlaceOnlineDialog(fieldOnlineList: List<Pair<String, Int>>) {

        val fieldOnlineListDialog = object : PlaceOnlineDialog(
            context,
            PlaceOnlineAdapter(fieldOnlineList)
        ) {

        }
        fieldOnlineListDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        fieldOnlineListDialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun showDatePickerDialog(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val minDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day}.${month + 1}.${year}")
        val maxDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day + 30}.${month + 1}.${year}")

        DatePickerDialog(
            requireContext(),
            { _, myYear, myMonth, myDay ->
                date = "${myDay}.${myMonth + 1}.${myYear}"
//                showTimePickerDialog(calendar)
//                val timePicker = TimePickerFragment(date)
//                timePicker.show(parentFragmentManager, "timePicker")
                showTimePickerDialog()

            }, year, month, day
        ).apply {
            if (minDateUnix != null && maxDateUnix != null) {
                datePicker.minDate = minDateUnix.time
                datePicker.maxDate = maxDateUnix.time

                show()

            }
        }


    }


    private fun showTimePickerDialog() {


        timePickerDialog = object : com.example.sport_path.dialogs.TimePickerDialog(
            context, this
        ) {

        }
        timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        timePickerDialog.show()
    }
//    fun showTimePickerDialog(calendar: Calendar){
//        val hour = calendar.get(Calendar.HOUR)
//        val minute = calendar.get(Calendar.MINUTE)
//        TimePickerDialog
////        TimePickerDialog(
////            requireContext(),
////            { _, hourOfDay, _ ->
////                date += " $hourOfDay:00"
////                Log.d("dsff", date.toString())
////
////
////            }, hour, minute, true
////        ).apply {
////            show()
////        }
//
//
//
//    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this frfasagment

//        return inflater.inflate(R.layout.fragment_modal_bottom_sheet, container, false)
        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }

    override fun onClick(time: String) {
        date += ' ' + time
        timePickerDialog.dismiss()
        if (WifiChecker.isInternetConnected(requireContext())){
        if (true !in entriesList.map {place.id == it.placeId || it.time == date }){

            ServiceLocator.getService<UsersViewModel>("UsersViewModel")!!.setEntry(place.id,date)
        }
        else{
            Toast.makeText(context, "Такая запись уже существует", Toast.LENGTH_LONG).show()
        }}
        else{
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()

        }


        this.dismiss()
        Log.d("mlog", date)
    }
}

