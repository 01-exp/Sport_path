package com.example.auth.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.auth.data.AuthStorage
import com.example.auth.data.retrofit.Interface.AuthRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepositoryImpl(private val retrofitService: AuthRetrofitService,
                         private val storage: AuthStorage
): AuthRepository {
    override fun regNewUser(
        userName: String,
        login: String,
        password: String,
        userIdMutableLiveData: MutableLiveData<Int>
    ) {
        retrofitService.regNewUser(userName, login, password)
            .enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.isSuccessful) {
                        userIdMutableLiveData.value = response.body()!!.toInt()
                        userIdMutableLiveData.value.let {
                            if (it != null) {
                                if (it > 0) {
                                    storage.saveUserLogged(true)
                                    storage.saveId(it.toString())
                                    storage.saveUserName(userName)
                                }
                            }
                        }
                    }
                }
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.d("mLogERR", t.message.toString())
                }
            })
    }

}