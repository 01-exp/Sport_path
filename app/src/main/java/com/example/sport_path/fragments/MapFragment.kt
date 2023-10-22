package com.example.sport_path.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sport_path.BuildConfig
import com.example.sport_path.R
import com.example.sport_path.databinding.FragmentMapBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApiKey(savedInstanceState)
        MapKitFactory.initialize(context)
        binding = FragmentMapBinding.inflate(layoutInflater)

       mapView = binding.mapView
        setStartPositinOnMap()
       setTestPoints()

    }
  fun setTestPoints(){
              val imageProvider = ImageProvider.fromResource(context, R.drawable.icon)

        val style = TextStyle().apply {
            size = 13.0f
            placement = TextStyle.Placement.TOP
        }

        mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(47.237422, 39.712262)
            setIcon(imageProvider)
            setText("Поршкеян тут",style)

        }

        mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(47.23668641935381,39.71187635010748)
            setIcon(imageProvider)
            setText("негр с газонокосилкой",style)

        }

        mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(47.239467141531335,39.7124612930925)
            setIcon(imageProvider)
            setText("Джувенс",style)

        }


        mapView.mapWindow.map.mapObjects.addPlacemark().apply {
            geometry = Point(47.242212995994976,39.71151033401034)
            setIcon(imageProvider)
            setText("я тут срал",style)

        }
  }
    fun setStartPositinOnMap()
    {
                mapView.mapWindow.map.move(
            CameraPosition(
                Point(47.237422, 39.712262),
                /* zoom = */ 17.5f,
                /* azimuth = */0.0f,
                /* tilt = */ 0.0f
            )
        )
    }

    private fun setApiKey(savedInstanceState: Bundle?) {
        val haveApiKey = savedInstanceState?.getBoolean("haveApiKey") ?: false
        if (!haveApiKey) {


            MapKitFactory.setApiKey(BuildConfig.API_KEY)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveApiKey", true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }



    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


}