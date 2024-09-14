package com.example.place.presentation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.WifiChecker
import com.example.place.R
import com.example.place.data.data_structures.Place
import com.example.place.databinding.FragmentModalBottomSheetBinding
import com.example.place.presentation.di.provider.PlaceComponentProvider
import com.example.place.presentation.picker_manager.PickerManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import kotlin.properties.Delegates

class ModalBottomSheetFragment() : BottomSheetDialogFragment() {
    //    TimePickerDialog.onClickListener
    private lateinit var binding: FragmentModalBottomSheetBinding
    private lateinit var date: String
    lateinit var time: String
//    private lateinit var timePickerDialog: TimePickerDialog
//    private lateinit var entriesList: MutableList<Entry>


    private lateinit var address: String
    private var placeId by Delegates.notNull<Int>()

    @Inject
    lateinit var placeViewModelFactory: PlaceViewModelFactory
    private lateinit var viewModel: PlaceViewModel

    @Inject
    lateinit var pickerManager: PickerManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentModalBottomSheetBinding.inflate(layoutInflater)

        (requireActivity().application as PlaceComponentProvider)
            .getPlaceComponent().inject(this)
        viewModel = ViewModelProvider(this, placeViewModelFactory)[PlaceViewModel::class.java]
        address = arguments?.getString("address").toString()
        placeId = arguments?.getInt("id")!!



        binding.nameTextView.text = Utils.cutAddress(address)
        binding.doEntryButton.setOnClickListener {
            if (WifiChecker.isInternetConnected(requireContext())) {
                val calendar = Calendar.getInstance()
//                showDatePickerDialog2(calendar)
                pickerManager.showDatePickerDialog(calendar, { _, myYear, myMonth, myDay -> })
            } else {
                Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
            }
        }
        binding.onlineButton.setOnClickListener {
            if (WifiChecker.isInternetConnected(requireContext())) {
                showPlaceOnlineDialog()
            } else {
                Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun showPlaceOnlineDialog() {
        val bundle = Bundle()
        bundle.putInt("id", placeId)
        findNavController().navigate(R.id.action_Place_to_PlaceOnline, bundle)


    }


    @SuppressLint("SimpleDateFormat")
    fun showDatePickerDialog2(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val minDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day}.${month + 1}.${year}")
        val maxDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day + 30}.${month + 1}.${year}")

        context?.let {
            DatePickerDialog(
                it, R.style.Theme_Sport_path_DatePicker,
                { _, myYear, myMonth, myDay ->

                    date = if ((myMonth + 1).toString().length < 2) {
                        "${myDay}.0${myMonth + 1}.${myYear}"
                    } else {
                        "${myDay}.${myMonth + 1}.${myYear}"
                    }
                    showTimePickerDialog()


                },
                year, month, day,
            ).apply {
                if (minDateUnix != null && maxDateUnix != null) {

                    datePicker.minDate = minDateUnix.time
                    datePicker.maxDate = maxDateUnix.time
                    show()
                }
            }

        }
    }


    private fun showTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> TODO("Not yet implemented") }
        context?.let {
            TimePickerDialog(
                it, R.style.Theme_Sport_path_TimePicker,
                { view, hourOfDay, minute ->

                },
                4,
                4,
                true
            ).apply {
                show()
            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


//    override fun onClick(time: String) {
////        date += " $time"
////        timePickerDialog.dismiss()
////        entriesList =
////            usersViewModel.entriesList.value!!
////
////        if (WifiChecker.isInternetConnected(requireContext())) {
////            if (true !in entriesList.map { (place.id == it.placeId) and (it.time == date) }) {
////                usersViewModel.setEntry(place.id, date)
////            } else {
////                Toast.makeText(context, "Такая запись уже существует", Toast.LENGTH_LONG).show()
////            }
////        } else {
////            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
////
////        }
////        this.dismiss()
//
//    }

    @SuppressLint("SimpleDateFormat")
    fun showDatePickerDialog(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val minDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day}.${month + 1}.${year}")
        val maxDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day + 30}.${month + 1}.${year}")

        context?.let {
            DatePickerDialog(
                it, R.style.Theme_Sport_path_DatePicker,
                { _, myYear, myMonth, myDay ->

                    date = if ((myMonth + 1).toString().length < 2) {
                        "${myDay}.0${myMonth + 1}.${myYear}"
                    } else {
                        "${myDay}.${myMonth + 1}.${myYear}"
                    }
                    viewModel.getPlaceOnline(placeId, date)
                },
                year, month, day,
            ).apply {
                if (minDateUnix != null && maxDateUnix != null) {

                    datePicker.minDate = minDateUnix.time
                    datePicker.maxDate = maxDateUnix.time
                    show()
                }
            }

        }
    }
}

