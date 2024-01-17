package com.example.sport_path.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sport_path.R
import com.example.sport_path.databinding.FragmentLoginBinding
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import com.example.sport_path.services.users.UsersViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: UsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)
        binding.registerTextView.setOnClickListener {
            ServiceLocator.getService<FragmentFactory>("FragmentFactory")
                ?.createFragment(FragmentFactory.FRAGMENT_REGISTRATION).let {
                    openFragment(it!!)
                }


        }

        viewModel = ServiceLocator.getService("UsersViewModel")!!
        val storage = ServiceLocator.getService<Storage>("Storage")!!

        viewModel.loginCode.observe(this) {
            Log.d("mLogVM",it.toString())
            if (it.loginStatusCode !=-5){
            when (it.loginStatusCode) {
                0 -> {ServiceLocator.getService<FragmentFactory>("FragmentFactory")
                    ?.createFragment(FragmentFactory.FRAGMENT_MAP).let {
                        openFragment(it!!)
                    }
                    storage.saveUserLogged(true)
                    storage.saveId(it.userId.toString())
                    storage.saveUserName(it.userName)


                }


                else -> Toast.makeText(
                    requireContext(),
                    "Неверные логин или пароль",
                    Toast.LENGTH_LONG
                ).show()

            }}

        }

        binding.loginButton.setOnClickListener {
            Log.d("mLogLogin", "click")
            binding.loginButton.isClickable = false
            viewModel.loginUser(
                binding.loginEditText.text.toString(),
                binding.passwordEditText.text.toString()
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
        ServiceLocator.getService<Router>("Router")?.addFragment(fragment, false)
    }


}