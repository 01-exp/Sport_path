package com.example.entry.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.WifiChecker
import com.example.entry.R
import com.example.entry.data.data_structures.Entry
import com.example.entry.databinding.FragmentEntriesBinding
import com.example.entry.presentation.di.provider.EntryComponentProvider

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class EntriesBottomSheetFragment : BottomSheetDialogFragment(),
    EntryAdapter.OnDeleteButtonClickListener {
    private lateinit var binding: FragmentEntriesBinding
    lateinit var confirmDialog: ConfirmDialogFragment


    @Inject
    lateinit var entryViewModelFactory: EntryViewModelFactory
    private lateinit var viewModel: EntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEntriesBinding.inflate(layoutInflater)
        (requireActivity().application as EntryComponentProvider)
            .getEntryComponent().inject(this)
        viewModel =
            ViewModelProvider(this, entryViewModelFactory)[EntryViewModel::class.java]

        viewModel.entriesList.observe(this) {
            binding.stateTextView.isVisible = it.isEmpty()
            Log.d("mLog","entry list has been updated")
            setUpRecyclerView(it)
        }
        if (WifiChecker.isInternetConnected(requireContext())) {
            viewModel.getUserEntries()
        } else {
            binding.stateTextView.isVisible = true
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun setUpRecyclerView(entryList: MutableList<Entry>) {
        val recyclerView = binding.rvEntryList
        val adapter = EntryAdapter(entryList, this@EntriesBottomSheetFragment)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    override fun onDeleteButtonClick(entryList: MutableList<Entry>, position: Int, size: Int) {
        if (WifiChecker.isInternetConnected(requireContext())) {
            val bundle = Bundle()
            bundle.putInt("id",entryList[position].id)
            bundle.putInt("position",position)
            findNavController().navigate(R.id.action_Entries_to_DelConfirm,bundle)
        } else {
            binding.stateTextView.isVisible = true
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        }

    }
}


