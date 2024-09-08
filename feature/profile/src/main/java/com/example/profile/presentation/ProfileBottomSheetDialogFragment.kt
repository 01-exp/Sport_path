package com.example.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBottomSheetDialogBinding
import com.example.profile.presentation.di.provider.ProfileComponentProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class ProfileBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentProfileBottomSheetDialogBinding

    @Inject
    lateinit var profileViewModelFactory: ProfileViewModelFactory
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBottomSheetDialogBinding.inflate(layoutInflater)

        (requireActivity().application as ProfileComponentProvider)
            .getProfileComponent().inject(this)
        viewModel =
            ViewModelProvider(this, profileViewModelFactory)[ProfileViewModel::class.java]


        binding.UserNameTextView.text = viewModel.getUserName()
        binding.outButton.setOnClickListener {
            findNavController().navigate(R.id.action_Profile_to_Confirm)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }
}