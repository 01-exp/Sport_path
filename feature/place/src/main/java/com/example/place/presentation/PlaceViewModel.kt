package com.example.place.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.place.data.data_structures.Place
import com.example.place.domain.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaceViewModel(private val repository: PlaceRepository) : ViewModel() {
    private val placeListMutable: MutableLiveData<List<Place>> = MutableLiveData()
    private val placeOnlineMapMutable = MutableLiveData<Map<String, Int>>()
    val placeList: LiveData<List<Place>> = placeListMutable
    val placeOnlineList: LiveData<Map<String, Int>> = placeOnlineMapMutable


    fun getPlaceOnline(placeId: Int, date:String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.getPlaceOnline(placeId,date, placeOnlineMapMutable)
            }
        }
    }

    fun setEntry(placeId: Int, time: String) {
        viewModelScope.launch {
            repository.setEntry(placeId, time)
        }
    }
}

class PlaceViewModelFactory(private val placeRepository: PlaceRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        PlaceViewModel(placeRepository) as T
}