package com.example.sport_path.services.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import com.example.sport_path.services.Retrofit.Interface.UserRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val userRetrofitService: UserRetrofitService) {
    val storage = ServiceLocator.getService<Storage>("Storage")!!


    /**
     * файлик под вопросом, нужно как-то внедрить этот класс в mvvm +retorfit
     */


//    fun reqNewUser(): LiveData<String> {
//        val id = MutableLiveData<String>()
//        userRetrofitService.setNewUser().enqueue(
//            object : Callback<String> {
//                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    if (response.isSuccessful) {
//                        id.value = response.body()
//                    } else {
//                        // Обработка ошибки
//                    }
//                }
//
//                override fun onFailure(call: Call<String>, throwable: Throwable) {
//                    // Обработка ошибки
//                }
//            }
//        )
//        return id
//
//    }

    fun getUserEntries(): LiveData<MutableList<Entry>> {
        val id = storage.getUserId()
        val userEntryList = MutableLiveData<MutableList<Entry>>()
        userRetrofitService.getUserEntries(id).enqueue(
            object : Callback<MutableList<Entry>> {
                override fun onResponse(
                    call: Call<MutableList<Entry>>,
                    response: Response<MutableList<Entry>>
                ) {
                    if (response.isSuccessful) {
                        userEntryList.value = response.body()
                    } else {
                        // Обработка ошибки
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<Entry>>,
                    throwable: Throwable
                ) {
                    // Обработка ошибки
                }
            }
        )
        return userEntryList
    }


    fun setEntry(placeId:Int, time:String){
        val id = storage.getUserId()

        userRetrofitService.setEntry(id,placeId,time).enqueue(
            object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
//                    if (response.isSuccessful) {
//
//                    } else {
//                        // Обработка ошибки
//                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    throwable: Throwable
                ) {
                    // Обработка ошибки
                }
            }
        )
    }


    fun delete_entry(entryId:Int){

        userRetrofitService.deleteEntry(entryId).enqueue(
            object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
//                    if (response.isSuccessful) {
//
//                    } else {
//                        // Обработка ошибки
//                    }
                }

                override fun onFailure(
                    call: Call<String>,
                    throwable: Throwable
                ) {
                    // Обработка ошибки
                }
            }
        )

    }

}