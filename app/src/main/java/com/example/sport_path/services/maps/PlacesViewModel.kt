package com.example.sport_path.services.maps

import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.services.Storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlacesViewModel(private val sport: Sport) : ViewModel() {

    private val   placeListMutable : MutableLiveData<List<Place>> = MutableLiveData()

    val placeList: LiveData<List<Place>> = placeListMutable

    fun loadPlaces(){

        viewModelScope.launch {
            val placeList = withContext(Dispatchers.IO) {
                Storage().getPlaceList(sport)

            }
            placeListMutable.value = placeList
        }
    }
}

class MainViewModelFactory(private val sport: Sport) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PlacesViewModel(sport) as T

}