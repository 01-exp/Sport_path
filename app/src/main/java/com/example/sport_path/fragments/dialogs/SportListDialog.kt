package com.example.sport_path.fragments.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R
import com.example.sport_path.services.maps.SportAdapter

// Define a DialogList class that extends Dialog
abstract class DialogList(
    context: Context?,
    var adapter: SportAdapter
) : Dialog(context!!) {


    // This method is called when the Dialog is created
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        setUpRecyclerView(view)
    }

    private fun setUpRecyclerView(view: View) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }
}