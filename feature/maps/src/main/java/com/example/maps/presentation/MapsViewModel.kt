package com.example.maps.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.maps.domain.MapsRepository
import com.example.maps.data.data_structures.Place
import com.example.maps.data.data_structures.Sport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MapsViewModel(private val repository: MapsRepository) : ViewModel() {
    private val placeListMutable: MutableLiveData<List<Place>> = MutableLiveData()
    val placeList: LiveData<List<Place>> = placeListMutable
    fun getPlaces() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getPlacesOnSport(placeListMutable)
            }
        }
    }
    fun getCurrentSport(): Sport =
         repository.getCurrentSport()
    fun setCurrentSport(sportName: String) =
        repository.setCurrentSport(sportName)

}

class MapsViewModelFactory(private val mapsRepository: MapsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        MapsViewModel(mapsRepository) as T
}