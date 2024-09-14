package com.example.place.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.place.data.PlaceStorage
import com.example.place.data.retrofit.Interface.PlaceRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRepositoryImpl(private val retrofitService: PlaceRetrofitService,private val storage: PlaceStorage):PlaceRepository {
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
                        Log.d("mLogErr-placeOnline not succ",response.body().toString())
                    }
                }
                override fun onFailure(call: Call<Map<String, Int>>, throwable: Throwable) {
                    // Обработка ошибки
                    Log.d("mLogErr-placeOnline fail",throwable.toString())

                }
            }
        )
    }

    override fun setEntry(placeId: Int, time: String) {
        val id = storage.getUserId()
        retrofitService.setEntry(id, placeId, time).enqueue(
            object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    Log.d("mLog", "$placeId, entrines")
                }

                override fun onFailure(
                    call: Call<String>,
                    throwable: Throwable
                ) {
                    Log.d("mLogERR", "$placeId, entrines")
                }
            }
        )
    }
}