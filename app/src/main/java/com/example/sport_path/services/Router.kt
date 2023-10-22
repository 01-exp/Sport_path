package com.example.sport_path.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class Router(private val containerId: Int, private val fragmentManager: FragmentManager) {
    fun addFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
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
}