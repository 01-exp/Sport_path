package com.example.entry.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.entry.databinding.FragmentConfirmDialogBinding
import com.example.entry.presentation.di.provider.EntryComponentProvider
import javax.inject.Inject


class ConfirmDialogFragment() :
    DialogFragment() {
    private lateinit var binding: FragmentConfirmDialogBinding

    @Inject
    lateinit var entryViewModelFactory: EntryViewModelFactory
    private lateinit var viewModel: EntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentConfirmDialogBinding.inflate(layoutInflater)

        (requireActivity().application as EntryComponentProvider).getEntryComponent()
            .inject(this)
        viewModel = ViewModelProvider(this, entryViewModelFactory)[EntryViewModel::class.java]

        val id = arguments?.getInt("id")!!
        val position = arguments?.getInt("position")!!
        binding.positiveButton.setOnClickListener {
            viewModel.deleteEntry(id,position)
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