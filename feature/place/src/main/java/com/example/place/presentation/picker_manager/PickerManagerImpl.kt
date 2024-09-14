package com.example.place.presentation.picker_manager

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import com.example.place.R
import java.text.SimpleDateFormat
import java.util.Calendar

class PickerManagerImpl(private val context: Context):PickerManager {
    @SuppressLint("SimpleDateFormat")
    override fun showDatePickerDialog(calendar: Calendar, listener: DatePickerDialog.OnDateSetListener) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val minDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day}.${month + 1}.${year}")
        val maxDateUnix = SimpleDateFormat("dd.MM.yyyy").parse("${day + 30}.${month + 1}.${year}")

        context.let {
            DatePickerDialog(
                it, R.style.Theme_Sport_path_DatePicker,
                listener, year, month, day,
            ).apply {
                if (minDateUnix != null && maxDateUnix != null) {

                    datePicker.minDate = minDateUnix.time
                    datePicker.maxDate = maxDateUnix.time
                    show()
                }
            }

        }
    }

//    { _, myYear, myMonth, myDay ->
//
//        date = if ((myMonth + 1).toString().length < 2) {
//            "${myDay}.0${myMonth + 1}.${myYear}"
//        } else {
//            "${myDay}.${myMonth + 1}.${myYear}"
//        }
//        viewModel.getPlaceOnline(placeId, date)
//        binding.dateTextView.text = Utils.formattedDate(date)
//
//    }



    override fun showTimePickerDialog(listener: OnTimeSetListener) {
        context.let {
            TimePickerDialog(
                it,R.style.Theme_Sport_path_TimePicker,
                listener,
                4,
                4,
                true
            ).apply {
                show()
            }

        }
    }

//    { view, hourOfDay, minute ->
//
//    }
}