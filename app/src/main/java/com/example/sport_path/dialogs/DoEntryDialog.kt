package com.example.sport_path.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sport_path.R
import com.example.sport_path.services.maps.SportAdapter

abstract class DoEntryDialog(
    context: Context?,

) : Dialog(context!!) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState ?: Bundle())
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_do_entry_dialog, null)

        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
    }
}