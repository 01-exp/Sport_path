package com.example.sport_path.fragments.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sport_path.R
import com.example.sport_path.databinding.FragmentConfirmDialogBinding


abstract class ConfirmFragmentDialog(context: Context?, private val listener: onClickListener) :
    Dialog(context!!) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_confirm_dialog, null)
        setContentView(view)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        findViewById<Button>(R.id.positive_button).setOnClickListener {
            listener.onClick()
            dismiss()
        }
        findViewById<Button>(R.id.negative_button).setOnClickListener {
            dismiss()
        }
    }


    interface onClickListener {
        fun onClick()
    }




}