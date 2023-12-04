package com.example.sport_path.dialogs

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.NumberPicker
import com.example.sport_path.data_structures.Place
import com.example.sport_path.databinding.FragmentModalBottomSheetBinding
import com.example.sport_path.services.maps.SportAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.time.Month
import java.time.Year
import java.util.Calendar

class ModalBottomSheetDialog(private var place: Place) : BottomSheetDialogFragment(),DatePickerDialog.OnDateSetListener {
    lateinit var binding: FragmentModalBottomSheetBinding
    lateinit var date :String
    lateinit var time :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentModalBottomSheetBinding.inflate(layoutInflater)
        binding.nameTextView.text = place.text




        binding.button2.setOnClickListener {
            val calendar = Calendar.getInstance()
            showDatePickerDialog(calendar)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun showDatePickerDialog(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val minDateUnix = SimpleDateFormat("dd/MM/yyyy").parse("${day}/${month + 1}/${year}")
        val maxDateUnix = SimpleDateFormat("dd/MM/yyyy").parse("${day + 30}/${month + 1}/${year}")

        DatePickerDialog(
            requireContext(),
            { _, myYear, myMonth, myDay ->
                date = "${myDay}/${myMonth}/${myYear}"
                showTimePickerDialog(calendar)
            }
            , year, month, day
        ).apply {
            if (minDateUnix != null && maxDateUnix != null) {
                datePicker.minDate = minDateUnix.time
                datePicker.maxDate = maxDateUnix.time

                show()

            }
        }



    }

    fun showTimePickerDialog(calendar: Calendar){
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            requireContext(),
            { _, hourOfDay, _ ->
                date += " $hourOfDay:00"
                Log.d("dsff", date.toString())


            }, hour, minute, true
        ).show()


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

//        return inflater.inflate(R.layout.fragment_modal_bottom_sheet, container, false)
        return binding.root
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }
}

