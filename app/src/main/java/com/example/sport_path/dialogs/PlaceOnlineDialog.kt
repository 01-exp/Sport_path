package com.example.sport_path.dialogs


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R
import com.example.sport_path.services.maps.PlaceOnlineAdapter

// Define a DialogList class that extends Dialog
abstract class PlaceOnlineDialog(
    context: Context?,
    var adapter: PlaceOnlineAdapter
) : Dialog(context!!) {


    // This method is called when the Dialog is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())

        // Use the LayoutInflater to inflate the
        // dialog_list layout file into a View object
        val view = LayoutInflater.from(context).inflate(R.layout.place_online_dialog, null)

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
    }

    // This method sets up the RecyclerView in the dialog
    private fun setUpRecyclerView(view: View) {

    }
}