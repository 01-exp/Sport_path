package com.example.auth.presentation

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
import com.example.auth.R
import com.example.auth.databinding.FragmentAuthBinding
import com.example.auth.presentation.di.provider.AuthComponentProvider
import javax.inject.Inject


class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding

    @Inject
    lateinit var authViewModelFactory: AuthViewModelFactory
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAuthBinding.inflate(layoutInflater)

        (requireActivity().application as AuthComponentProvider)
            .getAuthComponent().inject(this)
        viewModel =
            ViewModelProvider(this, authViewModelFactory)[AuthViewModel::class.java]

        binding.loginTextView.setOnClickListener {
          findNavController().navigate(R.id.action_Auth_to_Login)
        }


        viewModel.userId.observe(this) {
            when (it) {
                Log.d("mLogERR pp",it.toString())
                -1 -> makeToast("Этот логин уже используется")
                -2 -> makeToast("Пользователь с таким именем уже существует")
                else -> {
                    findNavController().navigate(R.id.action_Auth_to_Maps)
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