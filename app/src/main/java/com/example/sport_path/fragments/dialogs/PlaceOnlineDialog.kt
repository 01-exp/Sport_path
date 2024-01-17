package com.example.sport_path.fragments.dialogs


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.example.sport_path.R

// Define a DialogList class that extends Dialog
abstract class PlaceOnlineDialog(
    context: Context?, val placeOnlineMap:  Map<String, Int>
) : Dialog(context!!) {


//    private lateinit var countTextView: TextView

    // This method is called when the Dialog is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())

        // Use the LayoutInflater to inflate the
        // dialog_list layout file into a View object
        val view = LayoutInflater.from(context).inflate(R.layout.place_online_dialog, null)
//        countTextView = findViewById(R.id.countTextView)
        // Set the dialog's content view
        // to the newly created View object
        setContentView(view)

        // Allow the dialog to be dismissed
        // by touching outside of it
        setCanceledOnTouchOutside(true)

        // Allow the dialog to be canceled
        // by pressing the back button
        setCancelable(true)
        // Set up the RecyclerView in the dialog
        val spinner = findViewById<Spinner>(R.id.time_spinner)
        ArrayAdapter.createFromResource(
            context,
            R.array.hours_and_minuts_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears.
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner.
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            val countTextView = findViewById<TextView>(R.id.countTextView)

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                countTextView.text = getOnline(parent?.getItemAtPosition(position).toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                countTextView.text = getOnline(parent?.getItemAtPosition(0).toString())

            }
        }
    }

    // This method sets up the RecyclerView in the dialog
    private fun getOnline(key:String):String {
        val count = placeOnlineMap[key]!!
        val postfix = if ((count == 0) or ((count%2)!=0) ){
            "человек"
        }else{
            "человека"
        }

        return "Прийдёт ${placeOnlineMap[key]} $postfix"

    }
}