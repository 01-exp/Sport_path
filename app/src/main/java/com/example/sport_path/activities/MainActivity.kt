package com.example.sport_path.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.sport_path.R
import com.example.sport_path.fragments.MapFragment
import com.example.sport_path.fragments.ProfileFragment
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initServices()
        ServiceLocator.getService<MapFragment>("MapFragment")?.let { openFragment(it) }
    }
    private fun initServices() {
        ServiceLocator.registerService("Router", Router(R.id.place_holder, supportFragmentManager))
        ServiceLocator.registerService("MapFragment",MapFragment())
        ServiceLocator.registerService("ProfileFragment",ProfileFragment())
    }



    private fun openFragment(fragment: Fragment) {
        ServiceLocator.getService<Router>("Router")?.addFragment(fragment, true)
    }

}
