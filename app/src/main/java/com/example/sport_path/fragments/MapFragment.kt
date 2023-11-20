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
import com.example.sport_path.dialogs.ModalBottomSheetDialog
import com.example.sport_path.services.Router
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.maps.PlacesViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MapFragment : Fragment(), SportAdapter.OnItemCLickListener {
    private lateinit var binding: FragmentMapBinding
    private lateinit var mapView: MapView
    private lateinit var dialogList: DialogList

    private lateinit var viewModel: PlacesViewModel
    private var currentSport = Sport("Баскетбол", R.drawable.backetball)
    private var currentPosition = Utils.startPosition
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(BuildConfig.API_KEY)
        MapKitFactory.initialize(context)
        binding = FragmentMapBinding.inflate(layoutInflater)

        val router = ServiceLocator.getService<Router>("Router")!!
        viewModel = ServiceLocator.getService<PlacesViewModel>("PlacesViewModel")!!


        mapView = binding.mapView
        setPositionOnMap(currentPosition)
        viewModel.placeList.observe(this) {
            setTestPoints(it)
        }

        binding.buttonPlus.setOnClickListener {
            currentPosition = CameraPosition(
                currentPosition.target,
                /* zoom = */ currentPosition.zoom * (100f / 95f),
                /* azimuth = */currentPosition.azimuth,
                /* tilt = */ currentPosition.tilt
            )
            setPositionOnMap(currentPosition)
        }

        binding.buttonMinus.setOnClickListener {
            currentPosition = CameraPosition(
                currentPosition.target,
                /* zoom = */ currentPosition.zoom * 0.95f,
                /* azimuth = */currentPosition.azimuth,
                /* tilt = */ currentPosition.tilt
            )
            setPositionOnMap(currentPosition)
        }



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
            SportAdapter(sportsList, this@MapFragment)
        ) {

        }
        dialogList.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialogList.show()
    }

    private fun setTestPoints(placeList: List<Place>) {
        mapView.mapWindow.map.mapObjects.clear()
        val imageProvider = ImageProvider.fromResource(context, R.drawable.icon)
        val style = TextStyle().apply {
            size = 13.0f
            placement = TextStyle.Placement.TOP
        }

        for (place in placeList) {
            mapView.mapWindow.map.mapObjects.addPlacemark().apply {
                geometry = place.point
                setIcon(imageProvider)
                setText(place.text, style)
                addTapListener{ _, _ -> goTo(place) }
            }

        }
    }

    private fun goTo(place: Place): Boolean {
        val modalBottomSheetFragment = ModalBottomSheetDialog(place)
        parentFragmentManager.let {
            modalBottomSheetFragment.show(
                it,
                modalBottomSheetFragment.tag
            )
        }


        return true
    }


    private fun setPositionOnMap(position: CameraPosition) {
        mapView.mapWindow.map.move(
            position
        )
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
        viewModel.loadPlaces(currentSport)
        dialogList.cancel()
    }


}