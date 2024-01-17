package com.example.sport_path.services.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sport_path.data_structures.Entry
import com.example.sport_path.data_structures.UserInfo
import com.example.sport_path.services.ServiceLocator
import com.example.sport_path.services.Storage
import com.example.sport_path.services.Retrofit.RetrofitServiseProvider
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel() : ViewModel() {


    private val userIdMutable: MutableLiveData<Int> = MutableLiveData()
    val userId: LiveData<Int> = userIdMutable

    private val entriesListMutable: MutableLiveData<MutableList<Entry>> = MutableLiveData()
    val entriesList: LiveData<MutableList<Entry>> = entriesListMutable

    private val loginCodeMutable:MutableLiveData<UserInfo> = MutableLiveData()
    val loginCode:LiveData<UserInfo> = loginCodeMutable




    val storage = ServiceLocator.getService<Storage>("Storage")!!
    val retrofitService = RetrofitServiseProvider.userRetrofitService

    fun getUserId() {
        viewModelScope.launch {
            val id = storage.getUserId()
                userIdMutable.value = id
            Log.d("mlogID", id.toString())
        }
        return
    }

   fun userOut(){
       viewModelScope.launch {
           loginCodeMutable.value = UserInfo(-5,"",-1)
       }
   }



    fun regNewUser(userName:String,login:String,password: String){
        viewModelScope.launch {
            val response = retrofitService.regNewUser(userName,login,password)
            response.enqueue(object :Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if(response.isSuccessful){
                        userIdMutable.value = response.body()!!.toInt()
                    }

                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("mLogERR", t.message.toString())
                }
            })
        }
    }


    fun getUserEntries() {

        val id = storage.getUserId()
        viewModelScope.launch {
            val response = retrofitService.getUserEntries(id)
            response.enqueue(object : Callback<MutableList<Entry>> {
                override fun onResponse(
                    call: Call<MutableList<Entry>>,
                    response: Response<MutableList<Entry>>
                ) {
                    if (response.isSuccessful) {
                        entriesListMutable.value = response.body()
                    } else {
                    }
                }

                override fun onFailure(call: Call<MutableList<Entry>>, throwable: Throwable) {
                    Log.d("mLogERR", throwable.message.toString())
                }
            })
        }
    }


    fun setEntry(placeId: Int, time: String) {
        viewModelScope.launch {
            val response = retrofitService.setEntry(storage.getUserId(), placeId, time)
            response.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {

                    Log.d("mLogCall", response.body().toString())
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("mLogERR", "$t   $call")
                }
            })

        }
    }

    fun deleteEntry(id: Int, position: Int) {
        viewModelScope.launch {
            val response = retrofitService.deleteEntry(id)
            response.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
//                    entriesListMutable.value?.removeAt(position)
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("mLogERR", "$t   $call")
                }
            })


        }
    }


    fun loginUser(login:String,password:String){
        Log.d("mLogVM","into loginUser")
        viewModelScope.launch {
            val response = retrofitService.loginUser(login,password)
            response.enqueue(object :Callback<UserInfo>{
                override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                    if (response.isSuccessful) {
                        loginCodeMutable.value = response.body()
                    } else {

                    }
                }

                override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                    Log.d("mLogERR", "$t   $call")
                }

            })
        }
    }

}

class UsersViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = UsersViewModel() as T

}