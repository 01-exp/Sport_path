package com.example.auth.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.auth.databinding.FragmentLoginBinding
import com.example.auth.presentation.AuthViewModel
import com.example.auth.presentation.AuthViewModelFactory
import com.example.auth.presentation.FragmentFactory

import javax.inject.Inject



class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

   // @Inject
    lateinit var routerFactory: Router.Factory
    lateinit var router: Router

  //  @Inject
    lateinit var usersViewModelFactory: AuthViewModelFactory
    private lateinit var viewModel: AuthViewModel

    override fun onAttach(context: Context) {
       // context.appComponent.inject(this)
        router = routerFactory.create(parentFragmentManager)
        viewModel = ViewModelProvider(this, usersViewModelFactory)[AuthViewModel::class.java]
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)

        viewModel.loginCode.observe(this) {
            if (it.loginStatusCode != -5) {
                when (it.loginStatusCode) {
                    0 -> {
                        router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MAP)
                    }

                    else -> Toast.makeText(
                        requireContext(),
                        "Неверные логин или пароль",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


        binding.registerTextView.setOnClickListener {
            router.addFragmentWithBackStack(FragmentFactory.FRAGMENT_REGISTRATION)
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