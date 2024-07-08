package com.example.sport_path.fragments.bottomSheets

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sport_path.Utils
import com.example.sport_path.application.appComponent
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Place
import com.example.sport_path.databinding.FragmentModalBottomSheetBinding
import com.example.sport_path.fragments.dialogs.PlaceOnlineDialog
import com.example.sport_path.fragments.dialogs.TimePickerDialog
import com.example.sport_path.services.maps.PlaceViewModel
import com.example.sport_path.services.maps.PlaceViewModelFactory
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory
import com.example.sport_path.services.users.WifiChecker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

class ModalBottomSheetFragment(private var place: Place) : BottomSheetDialogFragment(),

    TimePickerDialog.onClickListener {
    private lateinit var binding: FragmentModalBottomSheetBinding
    private lateinit var date: String
    lateinit var time: String
    private lateinit var timePickerDialog: TimePickerDialog
    private lateinit var entriesList: MutableList<Entry>

    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory
    private lateinit var usersViewModel: UsersViewModel


    @Inject
    lateinit var placeViewModelFactory: PlaceViewModelFactory
    private lateinit var placeViewModel: PlaceViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        usersViewModel = ViewModelProvider(this,usersViewModelFactory)[UsersViewModel::class.java]
        placeViewModel = ViewModelProvider(this,placeViewModelFactory)[PlaceViewModel::class.java]
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentModalBottomSheetBinding.inflate(layoutInflater)
        binding.nameTextView.text = Utils.cutAddress(place.address)

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
                 showPlaceOnlineDialog()
            } else {
                Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun showPlaceOnlineDialog() {
        val placeOnlineDialog = object : PlaceOnlineDialog(placeViewModel,
            context,place.id,this
        ) {}
        placeOnlineDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        placeOnlineDialog.show()
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

                date = if ((myMonth + 1).toString().length < 2) {
                    "${myDay}.0${myMonth + 1}.${myYear}"
                } else {
                    "${myDay}.${myMonth + 1}.${myYear}"
                }
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
        timePickerDialog = object : TimePickerDialog(
            context, this
        ) {
        }
        timePickerDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        timePickerDialog.show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }



    override fun onClick(time: String) {
        date += " $time"
        timePickerDialog.dismiss()
        entriesList =
            usersViewModel.entriesList.value!!

        if (WifiChecker.isInternetConnected(requireContext())) {
            if (true !in entriesList.map { (place.id == it.placeId) and (it.time == date) }) {
                usersViewModel.setEntry(place.id, date)
            } else {
                Toast.makeText(context, "Такая запись уже существует", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()

        }
        this.dismiss()

    }
}

