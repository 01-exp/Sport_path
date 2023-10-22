package com.example.sport_path.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sport_path.BuildConfig
import com.example.sport_path.R

import com.example.sport_path.databinding.ActivityMainBinding
import com.example.sport_path.fragments.MapFragment
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.yandex.mapkit.mapview.MapView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initServices()
        openFragment(MapFragment())




//        MapKitFactory.setApiKey(key)
//        MapKitFactory.initialize(this)

//        mapView = findViewById(R.id.map_view)


//        mapView.mapWindow.map.move(
//            CameraPosition(
//                Point(47.237422, 39.712262),
//                /* zoom = */ 17.5f,
//                /* azimuth = */0.0f,
//                /* tilt = */ 0.0f
//            )
//        )



        //47.242212995994976,39.71151033401034
    }

//    private val placemarkTapListener = MapObjectTapListener { _, point ->
//        Toast.makeText(
//            this@MainActivity,
//            "Tapped the point (${point.longitude}, ${point.latitude})",
//            Toast.LENGTH_SHORT
//        ).show()
//        true
//    }

    private fun initServices() {
        ServiceLocator.registerService("Router", Router(R.id.place_holder, supportFragmentManager))

    }

    private fun openFragment(fragment: Fragment) {
        ServiceLocator.getService<Router>("Router")?.addFragment(fragment, true)
    }


//            override

//    fun onStart() {
//        super.onStart()
////        MapKitFactory.getInstance().onStart()
////        mapView.onStart()
//    }
//
//    override fun onStop() {
////        mapView.onStop()
////        MapKitFactory.getInstance().onStop()
//        super.onStop()
//    }

}
