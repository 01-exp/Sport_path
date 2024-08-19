package com.example.sport_path.fragments.bottomSheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_path.application.appComponent
import com.example.maps.data.data_structures.Entry
import com.example.sport_path.databinding.FragmentEntriesBinding
import com.example.sport_path.fragments.dialogs.ConfirmFragmentDialogForEntries
import com.example.sport_path.services.users.EntryAdapter
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory
import com.example.sport_path.services.users.WifiChecker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject


class EntriesBottomSheetFragment : BottomSheetDialogFragment(),
    EntryAdapter.OnDeleteButtonClickListener, ConfirmFragmentDialogForEntries.onClickListener {

    private lateinit var binding: FragmentEntriesBinding
    lateinit var confirmDialog: ConfirmFragmentDialogForEntries


    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory
    private lateinit var viewModel: UsersViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        viewModel = ViewModelProvider(this,usersViewModelFactory)[UsersViewModel::class.java]

        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEntriesBinding.inflate(layoutInflater)


        viewModel.entriesList.observe(this) {
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
        if (entryList.isNotEmpty()) {
            binding.stateTextView.isVisible = false
        }
    }

    override fun onDeleteButtonClick(entryList: MutableList<Entry>, position: Int, size: Int) {
        if (WifiChecker.isInternetConnected(requireContext())) {
            confirmDialog =
                object : ConfirmFragmentDialogForEntries(context, this, entryList, position, size) {}
            confirmDialog.show()
        } else {
            binding.stateTextView.isVisible = true
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        }

    }


    override fun onClick(entryList: MutableList<Entry>, position: Int, size: Int) {
        viewModel.deleteEntry(entryList[position].id, position)
        entryList.removeAt(position)
        if (size == 1) {
            binding.stateTextView.isVisible = true
        }
        setUpRecyclerView(entryList)
    }


}


