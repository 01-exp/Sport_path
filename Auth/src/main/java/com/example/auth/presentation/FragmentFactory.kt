package com.example.auth.presentation

import androidx.fragment.app.Fragment
import com.example.auth.presentation.fragments.LoginFragment
import com.example.auth.presentation.fragments.RegistrationFragment


class FragmentFactory {
    fun createFragment(fragmentType: Int): Fragment {
        return when (fragmentType) {
            FRAGMENT_LOGIN -> LoginFragment()
            FRAGMENT_REGISTRATION -> RegistrationFragment()
            else -> throw IllegalArgumentException("Invalid fragment type")
        }
    }
    companion object {
        const val FRAGMENT_MAP = 1
        const val FRAGMENT_PROFILE = 2
        const val FRAGMENT_LOGIN = 3
        const val FRAGMENT_REGISTRATION = 4
    }
}