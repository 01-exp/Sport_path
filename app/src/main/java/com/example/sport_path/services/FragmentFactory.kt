package com.example.sport_path.services

import androidx.fragment.app.Fragment
import com.example.sport_path.fragments.MapFragment
import com.example.sport_path.fragments.ProfileFragment

class FragmentFactory {
    fun createFragment(fragmentType: Int): Fragment {
        return when (fragmentType) {
            FRAGMENT_MAP -> MapFragment()
            FRAGMENT_PROFILE -> ProfileFragment()
            else -> throw IllegalArgumentException("Invalid fragment type")
        }
    }
    companion object {
        const val FRAGMENT_MAP = 1
        const val FRAGMENT_PROFILE = 2
    }
}