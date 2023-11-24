package com.example.sport_path.services.profiles

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.services.Storage
import com.example.sport_path.services.maps.PlacesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfilesViewModel() : ViewModel() {
    private val eventsListMutable : MutableLiveData<List<Place>> = MutableLiveData()

    val eventsList: LiveData<List<Place>> = eventsListMutable

    fun loadPlaces(sport: Sport){

        viewModelScope.launch {
            val placeList = withContext(Dispatchers.IO) {
                Storage().getPlaceList(sport)

            }
            eventsListMutable.value = placeList
        }
    }
    fun deleteItemById(id: Int , itemId: Int) {
        viewModelScope.launch {
            val updatedList = withContext(Dispatchers.IO) {
                eventsListMutable.value.orEmpty().filter { it.id != itemId }
            }
            eventsListMutable.value = updatedList
        }


    }
}

class ProfilesViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfilesViewModel() as T

    }
}