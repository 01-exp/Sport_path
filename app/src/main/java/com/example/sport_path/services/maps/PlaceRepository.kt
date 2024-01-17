package com.example.sport_path.services.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_path.data_structures.Place
import com.example.sport_path.services.Retrofit.Interface.PlaceRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceRepository(private val placeRetrofitService: PlaceRetrofitService) {

    /**
     * файлик под вопросом, нужно как-то внедрить этот класс в mvvm +retorfit
     */


    suspend fun getPlacesOnSport(sportName: String): LiveData<List<Place>> {
        val mutablePlaceList = MutableLiveData<List<Place>>(mutableListOf())
        placeRetrofitService.getPlacesOnSport(sportName).enqueue(
            object : Callback<List<Place>> {

                override fun onResponse(call: Call<List<Place>>, response: Response<List<Place>>) {
                    Log.d("mLog",mutablePlaceList.value.toString()+"toCall")
                    if (response.isSuccessful) {
                        mutablePlaceList.value = response.body()
                        Log.d("mLog",mutablePlaceList.value.toString()+"2343456")

                    } else {
                        Log.d("mLog",mutablePlaceList.value.toString()+"2343456")


                    }
                }
                override fun onFailure(call: Call<List<Place>>, throwable: Throwable) {
                    Log.d("mLogERR",throwable.message.toString())

                }
            }
        )
        Log.d("mLog",mutablePlaceList.value.toString()+"tofun")



     return  mutablePlaceList
    }




    fun getPlaceOnline(fieldId:Int,date:String):LiveData<Map<String,Int>>{
        val mutableOnlineMap = MutableLiveData<Map<String,Int>>()
        placeRetrofitService.getPlaceOnline(fieldId, date).enqueue(
            object : Callback<Map<String,Int>> {
                override fun onResponse(call: Call<Map<String,Int>>, response: Response<Map<String,Int>>) {
                    if (response.isSuccessful) {
                        mutableOnlineMap.value = response.body()
                    } else {
                        // Обработка ошибки
                    }
                }
                override fun onFailure(call: Call<Map<String,Int>>, throwable: Throwable) {
                    // Обработка ошибки
                }


            }
        )
        return mutableOnlineMap
    }

}