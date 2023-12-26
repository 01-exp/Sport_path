package com.example.sport_path.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.sport_path.dialogs.DialogList
import com.example.sport_path.data_structures.Place
import com.example.sport_path.R
import com.example.sport_path.services.maps.SportAdapter
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.databinding.FragmentMapBinding
import com.example.sport_path.dialogs.ModalBottomSheetDialog
import com.example.sport_path.services.FragmentFactory
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import com.example.sport_path.services.maps.PlacesViewModel
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.WifiChecker
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MapFragment : Fragment(), SportAdapter.OnItemCLickListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var dialogList: DialogList


    private lateinit var viewModel: PlacesViewModel
    private var currentSport = Utils.Sports[0]
    private var currentPosition = Utils.startPosition
    private var mapObjectTapListenerList = mutableListOf<MapObjectTapListener>()


    private lateinit var usersViewModel: UsersViewModel
    private lateinit var entriesList: MutableList<Entry>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMapBinding.inflate(layoutInflater)

        val router = ServiceLocator.getService<Router>("Router")!!
        val storage = ServiceLocator.getService<Storage>("Storage")!!
        viewModel = ServiceLocator.getService<PlacesViewModel>("PlacesViewModel")!!
        usersViewModel = ServiceLocator.getService("UsersViewModel")!!

        mapView = binding.mapView
        mapView.mapWindow.map.isNightModeEnabled = true
        setPositionOnMap(currentPosition, Animation(Animation.Type.SMOOTH, 1f))

        viewModel.placeList.observe(this) {
            setTestPoints(it)
        }
        usersViewModel.entriesList.observe(this) {
            entriesList = it
        }
        usersViewModel.getUserEntries()


        val currentSport = storage.getCurrentSport()
        if (WifiChecker.isInternetConnected(requireContext())) {
            viewModel.loadPlaces(currentSport)
            setUpSportCard(currentSport)
        } else {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        }

        binding.buttonPlus.setOnClickListener {
            currentPosition = CameraPosition(
                currentPosition.target,
                /* zoom = */ currentPosition.zoom * (100f / 95f),
                /* azimuth = */currentPosition.azimuth,
                /* tilt = */ currentPosition.tilt
            )
            setPositionOnMap(currentPosition, Animation(Animation.Type.SMOOTH, 0.5f))
        }

        binding.buttonMinus.setOnClickListener {
            currentPosition = CameraPosition(
                currentPosition.target,
                /* zoom = */ currentPosition.zoom * 0.95f,
                /* azimuth = */currentPosition.azimuth,
                /* tilt = */ currentPosition.tilt
            )
            setPositionOnMap(currentPosition, Animation(Animation.Type.SMOOTH, 0.5f))
        }



        binding.cardView.setOnClickListener {
            showSportsDialog()
        }
        binding.button.setOnClickListener {
//            ServiceLocator.getService<ProfileFragment>("ProfileFragment")
//                ?.let { router.replaceFragment(it, true) }
            val fragment = ServiceLocator.getService<FragmentFactory>("FragmentFactory")
                ?.createFragment(FragmentFactory.FRAGMENT_PROFILE)
            router.replaceFragment(fragment!!, true)
        }
    }


    private fun showSportsDialog() {
        val sportsList = Utils.Sports

        dialogList = object : DialogList(
            context,
            SportAdapter(sportsList, this@MapFragment)
        ) {

        }
        dialogList.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogList.show()
    }

    private fun setTestPoints(placeList: List<Place>) {
        mapView.mapWindow.map.mapObjects.clear()
        val imageProvider = ImageProvider.fromResource(context, R.drawable.marker)

        for (place in placeList) {
            val tapListener = MapObjectTapListener { _, _ -> goTo(place) }
            mapObjectTapListenerList.add(tapListener)
            mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = place.point
                setIcon(imageProvider)
                addTapListener(tapListener)
            }
        }
    }

    ///set_entry/2/18/17.12.2023%20
//19:00 HTTP/1.1" 200 -
    private fun goTo(place: Place): Boolean {
        usersViewModel.getUserEntries()
        val modalBottomSheetFragment = ModalBottomSheetDialog(place, entriesList)
        val position = CameraPosition(
            place.point,
            /* zoom = */ 17f,
            /* azimuth = */currentPosition.azimuth,
            /* tilt = */ currentPosition.tilt

        )
        setPositionOnMap(position, Animation(Animation.Type.SMOOTH, 0.5f))

        parentFragmentManager.let {
            modalBottomSheetFragment.show(
                it,
                modalBottomSheetFragment.tag
            )
        }


        return true
    }


    private fun setPositionOnMap(position: CameraPosition, animation: Animation? = null) {
        if (animation != null) {
            mapView.mapWindow.map.move(
                position,
                animation,
                null
            )
        } else {
            mapView.mapWindow.map.move(
                position
            )
        }
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
        if (WifiChecker.isInternetConnected(requireContext())) {
            currentSport = sport
            setUpSportCard(sport)
            viewModel.loadPlaces(currentSport)
            ServiceLocator.getService<Storage>("Storage")?.saveCurrentSport(sport.name)
        } else {
            Toast.makeText(context, "Нед подключения к интернету", Toast.LENGTH_LONG).show()
        }
        dialogList.cancel()
    }

    fun setUpSportCard(sport: Sport) {
        binding.sportName.text = sport.name
        binding.sportImage.setImageResource(sport.icon)
    }


}