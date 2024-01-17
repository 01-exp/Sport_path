package com.example.sport_path.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sport_path.BuildConfig
import com.example.sport_path.R
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.StorageImpl
import com.example.sport_path.services.maps.PlaceManager
import com.example.sport_path.services.maps.PlaceRepository
import com.example.sport_path.services.users.UserManager
import com.example.sport_path.services.maps.PlaceViewModel
import com.example.sport_path.services.maps.PlacesViewModelFactory
import com.example.sport_path.services.Retrofit.RetrofitServiseProvider
import com.example.sport_path.services.Storage

import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initServices()
        MapKitFactory.setApiKey(BuildConfig.API_KEY)
        MapKitFactory.initialize(this)
        val isLogged = ServiceLocator.getService<Storage>("Storage")?.userIsLogged()!!

        if(isLogged){
            ServiceLocator.getService<FragmentFactory>("FragmentFactory")?.createFragment(FragmentFactory.FRAGMENT_MAP).let {
                openFragment(it!!)
            }
        }
        else{
            ServiceLocator.getService<FragmentFactory>("FragmentFactory")?.createFragment(FragmentFactory.FRAGMENT_LOGIN).let {
                openFragment(it!!)
            }
        }



    }
    private fun initServices() {
        ServiceLocator.registerService("PlaceManager",PlaceManager())
        ServiceLocator.registerService("Router", Router(R.id.place_holder, supportFragmentManager))
        ServiceLocator.registerService("PlaceViewModel",
            ViewModelProvider(
                this,
                PlacesViewModelFactory()
            )[PlaceViewModel::class.java]
        )
        ServiceLocator.registerService("Storage",StorageImpl(this))
        ServiceLocator.registerService("UserManager", UserManager())
        ServiceLocator.registerService("UsersViewModel",
            ViewModelProvider(
                this,
                UsersViewModelFactory()
            )[UsersViewModel::class.java]
        )
        ServiceLocator.registerService("FragmentFactory",FragmentFactory())

    }
    private fun openFragment(fragment: Fragment) {
        ServiceLocator.getService<Router>("Router")?.addFragment(fragment, false)
    }

}
