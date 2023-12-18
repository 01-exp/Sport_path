package com.example.sport_path.services.maps

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.services.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlacesViewModel() : ViewModel() {
    private val placeManager = ServiceLocator.getService<PlaceManager>("PlaceManager")

    private val placeListMutable: MutableLiveData<List<Place>> = MutableLiveData()
    private val placeOnlineListMutable = MutableLiveData<List<Pair<String, Int>>>()


    val placeList: LiveData<List<Place>> = placeListMutable
    val placeOnlineList: LiveData<List<Pair<String, Int>>> = placeOnlineListMutable
    fun loadPlaces(sport: Sport) {

        viewModelScope.launch {
            val placeList = withContext(Dispatchers.IO) {
                placeManager?.getPlacesOnSport(sport)

            }
            placeListMutable.value = placeList
        }
    }


    fun loadPlaceOnline(placeId: Int) {
        viewModelScope.launch {
            val placeOnlineList = withContext(Dispatchers.IO) {
                placeManager?.getPlaceOnline(placeId)
            }
            placeOnlineListMutable.value = placeOnlineList
        }
    }
}

class PlacesViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PlacesViewModel() as T

}