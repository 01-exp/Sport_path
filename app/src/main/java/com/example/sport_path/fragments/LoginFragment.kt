package com.example.sport_path.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sport_path.R
import com.example.sport_path.databinding.FragmentLoginBinding
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory
import javax.inject.Inject
import com.example.sport_path.application.appComponent


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    @Inject
    lateinit var routerFactory: Router.Factory
    lateinit var router: Router

    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory
    private lateinit var viewModel: UsersViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        router = routerFactory.create(parentFragmentManager)
        viewModel = ViewModelProvider(this, usersViewModelFactory)[UsersViewModel::class.java]
        super.onAttach(context)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentLoginBinding.inflate(layoutInflater)

        viewModel.loginCode.observe(this) {
            if (it.loginStatusCode != -5) {
                when (it.loginStatusCode) {
                    0 -> {
                      //  router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MAP)
                     //   findNavController().navigate(R.id.action_Login_to_Maps)

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
          //  router.addFragmentWithBackStack(FragmentFactory.FRAGMENT_REGISTRATION)
          //  findNavController().navigate(R.id.action_Login_to_Auth)

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