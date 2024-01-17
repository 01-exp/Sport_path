package com.example.sport_path.fragments.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.sport_path.R
import kotlin.time.Duration.Companion.hours

// Define a DialogList class that extends Dialog
abstract class TimePickerDialog(
    context: Context?,val listener: onClickListener

) : Dialog(context!!) {
    private var hours = ""
    private var minuts = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_time_picker, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        val hoursSpinner = findViewById<Spinner>(R.id.hours_spinner)
        val minutsSpinner = findViewById<Spinner>(R.id.minuts_spinner)




        ArrayAdapter.createFromResource(
            context,
            R.array.hours_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            hoursSpinner.adapter = adapter

        }

        ArrayAdapter.createFromResource(
            context,
            R.array.mins_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            minutsSpinner.adapter = adapter

        }

        hoursSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                hours = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                hours = parent?.getItemAtPosition(0).toString()

            }
        }
        minutsSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                minuts = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                minuts = parent?.getItemAtPosition(0).toString()
            }

        }

        findViewById<Button>(R.id.accept_button).setOnClickListener{
           listener.onClick("$hours:$minuts")
        }

    }



    interface onClickListener{
        fun onClick(time:String)
    }

}
