package com.example.sport_path.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sport_path.data_structures.Place
import com.example.sport_path.databinding.FragmentModalBottomSheetBinding
import com.example.sport_path.services.maps.SportAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheetDialog(private var place: Place) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentModalBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentModalBottomSheetBinding.inflate(layoutInflater)
        binding.nameTextView.text = place.text
        binding.button2.setOnClickListener {
            val dialogList = object : DoEntryDialog(
                context,

            ) {

            }
//            dialogList.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialogList.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

//        return inflater.inflate(R.layout.fragment_modal_bottom_sheet, container, false)
        return binding.root
    }
}