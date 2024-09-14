package com.example.maps.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.core.WifiChecker
import com.example.maps.R
import com.example.maps.data.Utils
import com.example.maps.presentation.di.provider.MapsComponentProvider
import com.example.maps.databinding.FragmentMapsBinding
import com.example.maps.data.data_structures.Entry
import com.example.maps.data.data_structures.Place
import com.example.maps.data.data_structures.Sport
import com.example.maps.presentation.sport_list.DialogList
import com.example.maps.presentation.sport_list.SportAdapter
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import javax.inject.Inject

class MapsFragment : Fragment(), SportAdapter.OnItemCLickListener {
    private lateinit var binding: FragmentMapsBinding
    private lateinit var mapView: MapView
    private lateinit var dialogList: DialogList
    private var mapObjectTapListenerList = mutableListOf<MapObjectTapListener>()
    private lateinit var entriesList: MutableList<Entry>

    @Inject
    lateinit var mapsViewModelFactory: MapsViewModelFactory
    private lateinit var mapsViewModel: MapsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMapsBinding.inflate(layoutInflater)
        mapView = binding.mapView
        mapView.mapWindow.map.isNightModeEnabled = true


        (requireActivity().application as MapsComponentProvider)
            .getMapsComponent().inject(this)
        mapsViewModel = ViewModelProvider(this, mapsViewModelFactory)[MapsViewModel::class.java]


        binding.buttonPlus.setOnClickListener { zoomCamera(100f / 95f) }
        binding.buttonMinus.setOnClickListener { zoomCamera(0.95f) }
        binding.profileButton.setOnClickListener { showProfileDialog() }
        binding.cardView.setOnClickListener { showSportsDialog() }
        binding.button.setOnClickListener { showEntriesBottomSheet() }

        mapsViewModel.placeList.observe(this) {
            setPointsOnMap(it)
        }

//        usersViewModel.entriesList.observe(this) {
//            entriesList = it
//        }
//
        if (WifiChecker.isInternetConnected(requireContext())) {
            mapsViewModel.getPlaces()
            //   usersViewModel.getUserEntries()
            setUpSportCard()
        } else {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        }
    }

    private fun showEntriesBottomSheet() {
       findNavController().navigate(R.id.action_Maps_to_Entries)
    }

    private fun showProfileDialog() =
        findNavController().navigate(R.id.action_Maps_to_Profile)


    private fun showSportsDialog() {
        dialogList = object : DialogList(
            context,
            SportAdapter(Utils.Sports, this@MapsFragment)
        ) {}
        dialogList.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogList.show()
    }

    private fun setPointsOnMap(placeList: List<Place>) {
        mapView.mapWindow.map.mapObjects.clear()
        val imageProvider = ImageProvider.fromResource(context, R.drawable.marker)
        for (place in placeList) {
            val tapListener = MapObjectTapListener { _, _ -> goToPlace(place) }
            mapObjectTapListenerList.add(tapListener)
            mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = Point(place.lat, place.lon)
                setIcon(imageProvider)
                  addTapListener(tapListener)
            }
        }
    }

    private fun goToPlace(place: Place): Boolean {
        if (WifiChecker.isInternetConnected(requireContext())) {
//            usersViewModel.getUserEntries()
            val position = CameraPosition(
                Point(place.lat, place.lon),
                /* zoom = */ 17f,
                /* azimuth = */mapView.mapWindow.map.cameraPosition.azimuth,
                /* tilt = */ mapView.mapWindow.map.cameraPosition.tilt
            )
            setPositionOnMap(position, Animation(Animation.Type.SMOOTH, 0.5f))
            setUpModalBottomSheet(place)
        } else {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        }
        Log.d("mLogPlaceID",place.id.toString())
        return true
    }

    private fun setUpModalBottomSheet(place: Place) {
        val bundle = Bundle()
        bundle.putString("address",place.address)
        bundle.putInt("id",place.id)
        findNavController().navigate(R.id.action_Maps_to_Place,bundle)
    }

    private fun setPositionOnMap(position: CameraPosition, animation: Animation? = null) {
        if (animation != null) {
            mapView.mapWindow.map.move(
                position,
                animation,
                null
            )
        } else {
            mapView.mapWindow.map.move(position)
        }
    }

    private fun zoomCamera(zoomRatio: Float) {
        val cameraPosition = CameraPosition(
            mapView.mapWindow.map.cameraPosition.target,
            mapView.mapWindow.map.cameraPosition.zoom * zoomRatio,
            /* zoom = */
            /* azimuth = */mapView.mapWindow.map.cameraPosition.azimuth,
            /* tilt = */ mapView.mapWindow.map.cameraPosition.tilt
        )
        setPositionOnMap(cameraPosition, Animation(Animation.Type.SMOOTH, 0.5f))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setPositionOnMap(Utils.startPosition, Animation(Animation.Type.SMOOTH, 1f))
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
        if (WifiChecker.isInternetConnected(requireContext())) {
            mapsViewModel.getPlaces()
            mapsViewModel.setCurrentSport(sport.name)
            setUpSportCard()
        } else {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        }
        dialogList.cancel()
    }

    private fun setUpSportCard() {
        val sport = mapsViewModel.getCurrentSport()
        binding.sportName.text = sport.name
        binding.sportImage.setImageResource(sport.icon)
    }
}