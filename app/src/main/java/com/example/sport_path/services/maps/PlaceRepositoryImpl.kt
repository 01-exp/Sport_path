package com.example.sport_path.services.maps

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.sport_path.data_structures.Place
import com.example.sport_path.data_structures.Sport
import com.example.sport_path.services.Retrofit.Interface.PlaceRetrofitService
import com.example.sport_path.services.Storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface PlaceRepository {
    fun getPlacesOnSport(placeList: MutableLiveData<List<Place>>)
    fun getCurrentSport(): Sport
    fun setCurrentSport(sportName: String)
    fun getPlaceOnline(
        fieldId: Int,
        date: String,
        placeOnlineMapMutableLiveData: MutableLiveData<Map<String, Int>>
    )
}


class PlaceRepositoryImpl(
    private val retrofitService: PlaceRetrofitService,
    private val storage: Storage
):PlaceRepository {
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
    override fun getPlaceOnline(
        fieldId: Int,
        date: String,
        placeOnlineMapMutableLiveData: MutableLiveData<Map<String, Int>>
    ) {
        retrofitService.getPlaceOnline(fieldId, date).enqueue(
            object : Callback<Map<String, Int>> {
                override fun onResponse(
                    call: Call<Map<String, Int>>,
                    response: Response<Map<String, Int>>
                ) {
                    if (response.isSuccessful) {
                        placeOnlineMapMutableLiveData.value = response.body()
                    } else {
                        // Обработка ошибки
                    }
                }
                override fun onFailure(call: Call<Map<String, Int>>, throwable: Throwable) {
                    // Обработка ошибки
                }
            }
        )
    }
}