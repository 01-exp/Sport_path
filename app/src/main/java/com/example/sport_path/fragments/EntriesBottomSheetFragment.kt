package com.example.sport_path.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.databinding.FragmentEntriesBinding
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.users.EntryAdapter
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.WifiChecker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EntriesBottomSheetFragment : BottomSheetDialogFragment(), EntryAdapter.OnDeleteButtonClickListener {

    private lateinit var binding: FragmentEntriesBinding
    private lateinit var viewModel: UsersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentEntriesBinding.inflate(layoutInflater)
//        val router = ServiceLocator.getService<Router>("Router")!!
//        binding.button3.setOnClickListener {
//            ServiceLocator.getService<FragmentFactory>("FragmentFactory")?.createFragment(
//                FragmentFactory.FRAGMENT_MAP
//            ).let {
//                router.replaceFragment(it!!, true)
//            }
//        }
        viewModel = ServiceLocator.getService("UsersViewModel")!!


        viewModel.entriesList.observe(this) {
            Log.d("mlog", "entriesListPinged")

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

    fun setUpRecyclerView(entryList: MutableList<Entry>) {
        Log.d("mlog", "setUpENTRIES")
        val recyclerView = binding.rvEntryList
        val adapter = EntryAdapter(entryList, this@EntriesBottomSheetFragment)
        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.adapter = adapter
        if (entryList.isNotEmpty()) {
            binding.stateTextView.isVisible = false
        }
    }

    override fun onDeleteButtonClick(entryList: MutableList<Entry>, position: Int,size: Int) {
        if (WifiChecker.isInternetConnected(requireContext())) {


            viewModel.deleteEntry(entryList[position].id, position)
            entryList.removeAt(position)
            if (size == 1) {
                binding.stateTextView.isVisible = true

            }
            setUpRecyclerView(entryList)
        } else {
            binding.stateTextView.isVisible = true
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()

        }

    }


}


