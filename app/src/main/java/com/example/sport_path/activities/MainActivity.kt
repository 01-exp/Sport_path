package com.example.sport_path.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sport_path.R
import com.example.sport_path.fragments.MapFragment
import com.example.sport_path.fragments.ProfileFragment
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import com.example.sport_path.services.StorageImpl
import com.example.sport_path.services.users.UserManager
import com.example.sport_path.services.maps.PlacesViewModel
import com.example.sport_path.services.maps.PlacesViewModelFactory
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initServices()

        ServiceLocator.getService<MapFragment>("MapFragment")?.let { openFragment(it) }
//        ServiceLocator.getService<Storage>("Storage")?.deleteData()
    }
    private fun initServices() {
        ServiceLocator.registerService("Router", Router(R.id.place_holder, supportFragmentManager))
        ServiceLocator.registerService("MapFragment",MapFragment())
        ServiceLocator.registerService("ProfileFragment",ProfileFragment())
        ServiceLocator.registerService("PlacesViewModel",
            ViewModelProvider(
                this,
                PlacesViewModelFactory()
            )[PlacesViewModel::class.java]
        )
        ServiceLocator.registerService("Storage",StorageImpl(this))
        ServiceLocator.registerService("UserManager", UserManager())
        ServiceLocator.registerService("UsersViewModel",
            ViewModelProvider(
                this,
                UsersViewModelFactory()
            )[UsersViewModel::class.java]
        )
    }
    private fun openFragment(fragment: Fragment) {
        ServiceLocator.getService<Router>("Router")?.addFragment(fragment, true)
    }

}
