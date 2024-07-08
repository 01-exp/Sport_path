package com.example.sport_path.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.sport_path.fragments.dialogs.DialogList
import com.example.sport_path.data_structures.Place
import com.example.sport_path.R
import com.example.sport_path.services.maps.SportAdapter
import com.example.sport_path.Utils
import com.example.sport_path.application.appComponent
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.databinding.FragmentMapBinding
import com.example.sport_path.fragments.bottomSheets.EntriesBottomSheetFragment
import com.example.sport_path.fragments.bottomSheets.ModalBottomSheetFragment
import com.example.sport_path.fragments.bottomSheets.ProfileBottomSheetDialogFragment
import com.example.sport_path.services.maps.PlaceViewModel
import com.example.sport_path.services.maps.PlaceViewModelFactory
import com.example.sport_path.services.users.UsersViewModel
import com.example.sport_path.services.users.UsersViewModelFactory
import com.example.sport_path.services.users.WifiChecker
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import javax.inject.Inject

class MapFragment : Fragment(), SportAdapter.OnItemCLickListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var dialogList: DialogList
    private var mapObjectTapListenerList = mutableListOf<MapObjectTapListener>()
    private lateinit var entriesList: MutableList<Entry>

    @Inject
    lateinit var usersViewModelFactory: UsersViewModelFactory
    private lateinit var usersViewModel: UsersViewModel

    @Inject
    lateinit var placeViewModelFactory: PlaceViewModelFactory
    private lateinit var placeViewModel: PlaceViewModel

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        usersViewModel = ViewModelProvider(this, usersViewModelFactory)[UsersViewModel::class.java]
        placeViewModel = ViewModelProvider(this, placeViewModelFactory)[PlaceViewModel::class.java]
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentMapBinding.inflate(layoutInflater)
        mapView = binding.mapView
        mapView.mapWindow.map.isNightModeEnabled = true
        binding.buttonPlus.setOnClickListener { zoomCamera(100f / 95f) }
        binding.buttonMinus.setOnClickListener { zoomCamera(0.95f) }
        binding.profileButton.setOnClickListener { showProfileDialog() }
        binding.cardView.setOnClickListener { showSportsDialog() }
        binding.button.setOnClickListener { showEntriesBottomSheet() }

        placeViewModel.placeList.observe(this) {
            setPointsOnMap(it)
        }
        usersViewModel.entriesList.observe(this) {
            entriesList = it
        }

        if (WifiChecker.isInternetConnected(requireContext())) {
            placeViewModel.getPlaces()
            usersViewModel.getUserEntries()
            setUpSportCard()
        } else {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        }
    }

    private fun showEntriesBottomSheet() {
        val fragment = EntriesBottomSheetFragment()
        parentFragmentManager.let {
            fragment.show(
                it,
                fragment.tag
            )
        }
    }

    private fun showProfileDialog() {
        val profileBottomSheetDialogFragment = ProfileBottomSheetDialogFragment()
        parentFragmentManager.let {
            profileBottomSheetDialogFragment.show(
                it,
                profileBottomSheetDialogFragment.tag
            )
        }
    }

    private fun showSportsDialog() {
        dialogList = object : DialogList(
            context,
            SportAdapter(Utils.Sports, this@MapFragment)
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
            usersViewModel.getUserEntries()
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
        return true
    }

    private fun setUpModalBottomSheet(place: Place) {
        val modalBottomSheetFragment = ModalBottomSheetFragment(place)
        parentFragmentManager.let {
            modalBottomSheetFragment.show(
                it,
                modalBottomSheetFragment.tag
            )
        }
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
            placeViewModel.getPlaces()
            placeViewModel.setCurrentSport(sport.name)
            setUpSportCard()
        } else {
            Toast.makeText(context, "Нет подключения к интернету", Toast.LENGTH_LONG).show()
        }
        dialogList.cancel()
    }

    private fun setUpSportCard() {
        val sport = placeViewModel.getCurrentSport()
        binding.sportName.text = sport.name
        binding.sportImage.setImageResource(sport.icon)
    }
}