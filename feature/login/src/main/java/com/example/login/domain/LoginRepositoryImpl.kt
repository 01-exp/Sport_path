package com.example.login.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.login.data.LoginStorage
import com.example.login.data.data_structures.UserInfo
import com.example.login.data.retrofit.Interface.LoginRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepositoryImpl(private val retrofitService: LoginRetrofitService,
                          private val storage: LoginStorage):LoginRepository {
    override fun loginUser(
        login: String,
        password: String,
        loginCodeMutableLiveData: MutableLiveData<UserInfo>
    ) {
        retrofitService.loginUser(login, password).enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    loginCodeMutableLiveData.value = response.body()
                    if (loginCodeMutableLiveData.value?.loginStatusCode == 0) {
                        loginCodeMutableLiveData.value.let {
                            storage.saveUserLogged(true)
                            storage.saveId(it!!.userId.toString())
                            storage.saveUserName(it.userName)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.d("mLogERR", "$t   $call")
            }
        })
    }
}