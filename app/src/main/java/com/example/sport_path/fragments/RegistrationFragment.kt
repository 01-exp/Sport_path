package com.example.sport_path.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sport_path.R
import com.example.sport_path.databinding.FragmentRegistrationBinding
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import com.example.sport_path.services.users.UsersViewModel


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentRegistrationBinding.inflate(layoutInflater)
        binding.loginTextView.setOnClickListener {
            ServiceLocator.getService<FragmentFactory>("FragmentFactory")?.createFragment(
                FragmentFactory.FRAGMENT_LOGIN
            ).let {
                openFragment(it!!)
            }
        }
        val storage = ServiceLocator.getService<Storage>("Storage")!!
        viewModel = ServiceLocator.getService("UsersViewModel")!!
        viewModel.userId.observe(this) {
            when (it) {
                -1 -> Toast.makeText(
                    requireContext(),
                    "Этот логин уже используется",
                    Toast.LENGTH_LONG
                ).show()
                -2 -> Toast.makeText(
                    requireContext(),
                    "Пользователь с таким именем уже существует",
                    Toast.LENGTH_LONG
                ).show()
                else->{
                    storage.saveId(it.toString())
                    storage.saveUserName(binding.nameEditText.text.toString())
                    ServiceLocator.getService<FragmentFactory>("FragmentFactory")?.createFragment(
                        FragmentFactory.FRAGMENT_MAP
                    ).let { it2->
                        openFragment(it2!!)
                    }
                }
            }

        }
        binding.registerButton.setOnClickListener{
            viewModel.regNewUser(
                binding.nameEditText.text.toString(),
                binding.regLoginEditText.text.toString(),
                binding.regPasswordEditText.text.toString()
            )
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    fun openFragment(fragment: Fragment) {
        ServiceLocator.getService<Router>("Router")?.addFragment(fragment, true)
    }


}