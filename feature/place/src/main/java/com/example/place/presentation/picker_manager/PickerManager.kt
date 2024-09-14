package com.example.place.presentation.picker_manager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.util.Calendar

interface PickerManager {
    fun showDatePickerDialog(calendar: Calendar, listener: DatePickerDialog.OnDateSetListener)

    fun showTimePickerDialog(listener: TimePickerDialog.OnTimeSetListener)
}