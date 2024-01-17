package com.example.sport_path.services

import androidx.fragment.app.Fragment
import com.example.sport_path.fragments.MapFragment
import com.example.sport_path.fragments.EntriesBottomSheetFragment
import com.example.sport_path.fragments.LoginFragment
import com.example.sport_path.fragments.RegistrationFragment

class FragmentFactory {
    fun createFragment(fragmentType: Int): Fragment {
        return when (fragmentType) {
            FRAGMENT_MAP -> MapFragment()
            FRAGMENT_PROFILE -> EntriesBottomSheetFragment()
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