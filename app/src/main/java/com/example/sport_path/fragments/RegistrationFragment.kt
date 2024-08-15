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
import com.example.sport_path.application.appComponent
import com.example.sport_path.databinding.FragmentRegistrationBinding
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory
import javax.inject.Inject


class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding

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
        binding = FragmentRegistrationBinding.inflate(layoutInflater)

        binding.loginTextView.setOnClickListener {
            findNavController().navigate(R.id.action_Auth_to_Login)

           // router.addFragmentWithBackStack(FragmentFactory.FRAGMENT_LOGIN)
        }

        viewModel.userId.observe(this) {
            when (it) {
                -1 -> makeToast("Этот логин уже используется")

                -2 -> makeToast("Пользователь с таким именем уже существует")

                else -> {
                    findNavController().navigate(R.id.action_Auth_to_Maps)

                  //  router.addFragmentWithoutBackStack(FragmentFactory.FRAGMENT_MAP)
                }
            }

        }
        binding.registerButton.setOnClickListener {
            checkingEnteredFields()
        }

    }

    private fun checkingEnteredFields() {
        val passwordLength = binding.regPasswordEditText.text.toString().length
        val loginLength = binding.regLoginEditText.text.toString().length
        val nameLength = binding.nameEditText.text.toString().length

        if (passwordLength in 8 until 40) {
            viewModel.regNewUser(
                binding.nameEditText.text.toString(),
                binding.regLoginEditText.text.toString(),
                binding.regPasswordEditText.text.toString()
            )
        } else if (passwordLength >= 40) {
            makeToast("Слишком много символов в поле пароля")

        } else if (passwordLength < 8) {
            makeToast("Пароль должен содержать не менее 8-ми символов")
        } else if (loginLength >= 40) {
            makeToast("Слишком много символов в поле Логина")

        } else if (nameLength >= 40) {
            makeToast("Слишком много символов в имени Пользователя")

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun makeToast(text: String) {
        Toast.makeText(
            requireContext(),
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}