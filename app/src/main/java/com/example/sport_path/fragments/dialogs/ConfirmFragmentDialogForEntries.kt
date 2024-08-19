package com.example.sport_path.fragments.dialogs


import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import com.example.sport_path.R
import com.example.maps.data.data_structures.Entry


abstract class ConfirmFragmentDialogForEntries(
    context: Context?,
    private val listener: onClickListener,
    private val entryList: MutableList<Entry>,
    private val position: Int,
    private val size: Int
) :
    Dialog(context!!) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_confirm_dialog, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        findViewById<Button>(R.id.positive_button).setOnClickListener {
            listener.onClick(entryList, position, size)
            dismiss()
        }
        findViewById<Button>(R.id.negative_button).setOnClickListener {
            dismiss()
        }
    }


    interface onClickListener {
        fun onClick(entryList: MutableList<Entry>, position: Int, size: Int)
    }


}