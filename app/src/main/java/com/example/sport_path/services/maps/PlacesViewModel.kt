package com.example.sport_path.services.maps

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlacesViewModel() : ViewModel() {

    private val   placeListMutable : MutableLiveData<List<Place>> = MutableLiveData()


    val placeList: LiveData<List<Place>> = placeListMutable

    fun loadPlaces(sport: Sport){

        viewModelScope.launch {
            val placeList = withContext(Dispatchers.IO) {
                PlaceManager().getPlacesOnSport(sport)

            }
            placeListMutable.value = placeList
        }
    }
}

class PlacesViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PlacesViewModel() as T

}