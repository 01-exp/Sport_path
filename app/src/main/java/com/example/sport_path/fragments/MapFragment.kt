package com.example.sport_path.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sport_path.BuildConfig
import com.example.sport_path.dialogs.DialogList
import com.example.sport_path.data_structures.Place
import com.example.sport_path.R
import com.example.sport_path.services.maps.SportAdapter
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.databinding.FragmentMapBinding
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.maps.PlacesViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MapFragment : Fragment(), SportAdapter.OnItemCLickListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var dialogList: DialogList
    lateinit var viewModel : PlacesViewModel
    var currentSport = Sport("Баскетбол", R.drawable.backetball)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setApiKey(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.API_KEY)
        MapKitFactory.initialize(context)
        binding = FragmentMapBinding.inflate(layoutInflater)

        val router = ServiceLocator.getService<Router>("Router")!!
        mapView = binding.mapView
        setStartPositinOnMap()
        setTestPoints()





        binding.choiseSportImageView.setOnClickListener {
            showSportsDialog()
        }
        binding.button.setOnClickListener {
            ServiceLocator.getService<ProfileFragment>("ProfileFragment")
                ?.let { router.replaceFragment(it, true) }

        }
    }


    private fun showSportsDialog() {
        val sportsList = Utils.Sports

        dialogList = object : DialogList(
            context,
            SportAdapter(sportsList,this@MapFragment)
        ) {

        }
        dialogList.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogList.show()
    }

    private fun setTestPoints() {
        val imageProvider = ImageProvider.fromResource(context, R.drawable.icon)
        val placeList = listOf(
            Place(Point(47.237422, 39.712262), "Поршкеян тут"),
            Place(Point(47.23668641935381, 39.71187635010748), "негр с газонокосилкой"),
            Place(Point(47.239467141531335, 39.7124612930925), "Джувенс"),
            Place(Point(47.242212995994976, 39.71151033401034), "я тут срал")
        )
        val style = TextStyle().apply {
            size = 13.0f
            placement = TextStyle.Placement.TOP
        }
        for (place in placeList) {
            mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = place.point
                setIcon(imageProvider)
                setText(place.text, style)
            }
        }
    }

    private fun setStartPositinOnMap() {
        mapView.mapWindow.map.move(
            CameraPosition(
                Point(47.237422, 39.712262),
                /* zoom = */ 17.5f,
                /* azimuth = */0.0f,
                /* tilt = */ 0.0f
            )
        )
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

    override fun onItemCLick(sport: Sport) {
        currentSport = sport
        binding.sportName.text = sport.name
        binding.sportImage.setImageResource(sport.icon)
       dialogList.cancel()
    }


}