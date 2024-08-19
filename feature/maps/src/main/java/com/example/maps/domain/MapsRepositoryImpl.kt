package com.example.maps.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.maps.data.MapsStorage
import com.example.maps.data.retrofit.Interface.MapsRetrofitService
import com.example.maps.data.data_structures.Place
import com.example.maps.data.data_structures.Sport
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsRepositoryImpl(private val retrofitService: MapsRetrofitService,
                         private val storage: MapsStorage):
    MapsRepository {
    override fun getPlacesOnSport(placeList: MutableLiveData<List<Place>>) {
        val sport = storage.getCurrentSport()
        retrofitService.getPlacesOnSport(sport.nameEn).enqueue(
            object : Callback<List<Place>> {

                override fun onResponse(call: Call<List<Place>>, response: Response<List<Place>>) {
                    if (response.isSuccessful) {
                        placeList.value = response.body()
                    }
                }
                override fun onFailure(call: Call<List<Place>>, throwable: Throwable) {
                    Log.d("mLogERR", throwable.message.toString())
                }
            }
        )
    }
    override fun getCurrentSport(): Sport {
        return storage.getCurrentSport()
    }
    override fun setCurrentSport(sportName: String) {
        storage.saveCurrentSport(sportName)
    }
}