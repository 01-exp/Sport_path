package com.example.auth.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.auth.data.Retrofit.Interface.AuthRetrofitService
import com.example.auth.data.Storage
import com.example.sport_path.data_structures.UserInfo

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface AuthRepository {
    fun getUserName(): String
    fun isUserLogged(): Boolean
    fun userOut(loginCodeMutableLiveData: MutableLiveData<UserInfo>)
    fun getNewUser(
        userName: String,
        login: String,
        password: String,
        userIdMutableLiveData: MutableLiveData<Int>
    )
    fun loginUser(
        login: String,
        password: String,
        loginCodeMutableLiveData: MutableLiveData<UserInfo>
    )
}


class AuthRepositoryImpl(
    private val retrofitService: AuthRetrofitService,
    private val storage: Storage
):AuthRepository {
    override fun getUserName(): String =
        storage.getUserName()


    override fun isUserLogged(): Boolean =
        storage.userIsLogged()



    override fun userOut(loginCodeMutableLiveData: MutableLiveData<UserInfo>) {
        loginCodeMutableLiveData.value = UserInfo(-5, "", -1)
        storage.clearUserData()
    }

    override fun getNewUser(
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