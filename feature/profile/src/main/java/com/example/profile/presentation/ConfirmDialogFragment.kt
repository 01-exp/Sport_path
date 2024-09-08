package com.example.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.profile.R
import com.example.profile.databinding.FragmentConfirmDialogBinding
import com.example.profile.databinding.FragmentProfileBottomSheetDialogBinding
import com.example.profile.presentation.di.provider.ProfileComponentProvider
import javax.inject.Inject

class ConfirmDialogFragment : DialogFragment() {


    private lateinit var binding: FragmentConfirmDialogBinding

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var viewModel: ProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConfirmDialogBinding.inflate(layoutInflater)


        (requireActivity().application as ProfileComponentProvider).getProfileComponent()
            .inject(this)
        viewModel = ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]

        binding.positiveButton.setOnClickListener {
            viewModel.userOut()
            findNavController().navigate(R.id.action_Confirm_to_Login)
            dismiss()
        }
        binding.negativeButton.setOnClickListener {
            dismiss()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}