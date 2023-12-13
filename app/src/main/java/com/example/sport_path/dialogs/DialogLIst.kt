package com.example.sport_path.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Place
import com.example.sport_path.services.maps.SportAdapter
import com.yandex.mapkit.geometry.Point

// Define a DialogList class that extends Dialog
abstract class DialogList(
    context: Context?,
    var adapter: SportAdapter
) : Dialog(context!!) {


    // This method is called when the Dialog is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())

        // Use the LayoutInflater to inflate the
        // dialog_list layout file into a View object
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null)

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
        setUpRecyclerView(view)
    }

    // This method sets up the RecyclerView in the dialog
    private fun setUpRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}