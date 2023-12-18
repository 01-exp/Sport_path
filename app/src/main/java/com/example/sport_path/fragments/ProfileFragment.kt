package com.example.sport_path.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Place
import com.example.sport_path.databinding.FragmentProfileBinding
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.users.UsersViewModel
import com.yandex.mapkit.geometry.Point


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: UsersViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val router = ServiceLocator.getService<Router>("Router")!!
        binding.Button.setOnClickListener {
            ServiceLocator.getService<MapFragment>("MapFragment")?.let {
                router.replaceFragment(it, true)
            }
        }
        viewModel = ServiceLocator.getService<UsersViewModel>("UsersViewModel")!!
        viewModel.userId.observe(this){
            Toast.makeText(context,it.toString(), Toast.LENGTH_LONG).show()

        }
        viewModel.getUserId()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    



}