package com.example.sport_path.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.databinding.FragmentProfileBinding
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.users.EntryAdapter
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.WifiChecker


class ProfileFragment : Fragment(), EntryAdapter.OnDeleteButtonClickListener {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UsersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val router = ServiceLocator.getService<Router>("Router")!!
        binding.button3.setOnClickListener {
            ServiceLocator.getService<FragmentFactory>("FragmentFactory")?.createFragment(
                FragmentFactory.FRAGMENT_MAP
            ).let {
                router.replaceFragment(it!!, true)
            }
        }
        viewModel = ServiceLocator.getService("UsersViewModel")!!


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

    fun setUpRecyclerView(entryList: List<Entry>) {
        Log.d("dsf", "pizda")
        val recyclerView = binding.rvEntryList
        val adapter = EntryAdapter(entryList, this@ProfileFragment)
        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.adapter = adapter
        if (entryList.isNotEmpty()) {
            binding.stateTextView.isVisible = false
        }
    }

    override fun onDeleteButtonClick(entry: Entry, position: Int) {
        viewModel.deleteEntry(position)
    }


}


