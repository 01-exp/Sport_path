package com.example.sport_path.services.maps

import android.util.Log
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sport_path.Utils
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Retrofit.RetrofitServiseProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceViewModel() : ViewModel() {


    private val placeListMutable: MutableLiveData<List<Place>> = MutableLiveData()
    private val placeOnlineListMutable = MutableLiveData<Map<String, Int>>()


    val placeList: LiveData<List<Place>> = placeListMutable
    val placeOnlineList: LiveData<Map<String, Int>> = placeOnlineListMutable

    fun loadPlaces(sport: Sport) {
        viewModelScope.launch {
            val response =
                RetrofitServiseProvider.placeRetrofitService.getPlacesOnSport(sport.nameEn)
            withContext(Dispatchers.IO) {
                response.enqueue(object : Callback<List<Place>> {
                    override fun onResponse(
                        call: Call<List<Place>>,
                        response: Response<List<Place>>
                    ) {
                        if (response.isSuccessful) {
                            response.body()
                            placeListMutable.value = response.body()
                        } else {
                            placeListMutable.value = listOf(Place("213", 0, 47.0, 40.0))
                        }
                    }

                    override fun onFailure(call: Call<List<Place>>, throwable: Throwable) {
                        Log.d("mLogERR", "$throwable   $call")
                    }
                })
            }
        }
    }


    fun loadPlaceOnline(placeId: Int) {
        viewModelScope.launch {
            val response =
                RetrofitServiseProvider.placeRetrofitService.getPlaceOnline(placeId,Utils.getTodayDate())
            withContext(Dispatchers.IO) {
                response.enqueue(object : Callback<Map<String,Int>> {
                    override fun onResponse(
                        call: Call<Map<String,Int>>,
                        response: Response<Map<String,Int>>
                    ) {
                        if (response.isSuccessful) {
                            response.body()
                            placeOnlineListMutable.value = response.body()
                        } else {
                            TODO("что то ")
                        }
                    }

                    override fun onFailure(call: Call<Map<String,Int>>, throwable: Throwable) {
                        Log.d("mLogERR", "$throwable   $call")
                    }
                })
            }
        }
    }
}

class PlacesViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = PlaceViewModel() as T

}