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

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {
    private val placeListMutable: MutableLiveData<List<Place>> = MutableLiveData()
    private val placeOnlineMapMutable = MutableLiveData<Map<String, Int>>()
    val placeList: LiveData<List<Place>> = placeListMutable
    val placeOnlineList: LiveData<Map<String, Int>> = placeOnlineMapMutable
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

    fun getPlaceOnline(placeId: Int, date:String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getPlaceOnline(placeId,date, placeOnlineMapMutable)
            }
        }
    }
}

class PlaceViewModelFactory(private val placeRepository: PlaceRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlaceViewModel(placeRepository) as T
}