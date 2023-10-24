package com.example.sport_path.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sport_path.databinding.FragmentProfileBinding
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator


class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val router = ServiceLocator.getService<Router>("Router")!!
        binding.button3.setOnClickListener {
            ServiceLocator.getService<MapFragment>("MapFragment")?.let {
                router.replaceFragment(it,true)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


}