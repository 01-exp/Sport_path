package com.example.auth.presentation.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.auth.R
import com.example.auth.presentation.FragmentFactory

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class Router @AssistedInject constructor(
    @Assisted("FragmentManager") val fragmentManager: FragmentManager,
    private val fragmentFactory: FragmentFactory,
    private val containerId: Int
) {


    fun addFragmentWithBackStack(fragmentType:Int) {
        val fragment = fragmentFactory.createFragment(fragmentType)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun addFragmentWithoutBackStack(fragmentType:Int) {
        val fragment = fragmentFactory.createFragment(fragmentType)
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment)
        transaction.commit()
    }

    fun replaceFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun popFragment() {
        fragmentManager.popBackStack()

    }
    @AssistedFactory
    interface Factory{
        fun create(@Assisted("FragmentManager") fragmentManager: FragmentManager):Router
    }


}