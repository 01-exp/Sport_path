package com.example.login.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.login.R
import com.example.login.databinding.FragmentLoginBinding
import com.example.login.presentation.di.provider.LoginComponentProvider
import javax.inject.Inject


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)

        (requireActivity().application as LoginComponentProvider)
            .getLoginComponent().inject(this)

        viewModel =
            ViewModelProvider(this, loginViewModelFactory)[LoginViewModel::class.java]

        viewModel.loginCode.observe(this) {
            if (it.loginStatusCode != -5) {
                when (it.loginStatusCode) {
                    0 -> {
                        findNavController().navigate(R.id.action_Login_to_Maps)
                    }
                    else ->{ Log.d("mLog12",it.loginStatusCode.toString())
                        Toast.makeText(

                        requireContext(),
                        "Неверные логин или пароль",
                        Toast.LENGTH_LONG
                    ).show()
                    }
                }
                binding.loginButton.isClickable = true
            }
        }


        binding.registerTextView.setOnClickListener {
            findNavController().navigate(R.id.action_Login_to_Auth)
        }
        binding.loginButton.setOnClickListener {
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
        return binding.root
    }
}