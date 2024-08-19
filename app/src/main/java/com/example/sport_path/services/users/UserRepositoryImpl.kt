package com.example.sport_path.services.users

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.maps.data.data_structures.Entry
import com.example.maps.data.data_structures.UserInfo
import com.example.sport_path.services.Retrofit.Interface.UserRetrofitService
import com.example.sport_path.services.Storage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface UserRepository {
    fun getUserName(): String
    fun isUserLogged(): Boolean
    fun userOut(loginCodeMutableLiveData: MutableLiveData<UserInfo>)
    fun getNewUser(
        userName: String,
        login: String,
        password: String,
        userIdMutableLiveData: MutableLiveData<Int>
    )
    fun getUserEntries(entriesListMutableLiveData: MutableLiveData<MutableList<Entry>>)
    fun setEntry(placeId: Int, time: String)
    fun deleteEntry(entryId: Int)
    fun loginUser(
        login: String,
        password: String,
        loginCodeMutableLiveData: MutableLiveData<UserInfo>
    )
}


class UserRepositoryImpl(
    private val retrofitService: UserRetrofitService,
    private val storage: Storage
):UserRepository {
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

    override fun getUserEntries(entriesListMutableLiveData: MutableLiveData<MutableList<Entry>>) {
        val id = storage.getUserId()
        retrofitService.getUserEntries(id).enqueue(
            object : Callback<MutableList<Entry>> {
                override fun onResponse(
                    call: Call<MutableList<Entry>>,
                    response: Response<MutableList<Entry>>
                ) {
                    if (response.isSuccessful) {
                        entriesListMutableLiveData.value = response.body()
                        Log.d("mLogERR","GetEntirues")
                    } else {
                        Log.d("mLogERR","ELSEGetEntirues")
                    }
                }
                override fun onFailure(
                    call: Call<MutableList<Entry>>,
                    throwable: Throwable
                ) {
                    Log.d("mLogERR","onFailureGetEntirues")
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
                    Log.d("mLog","$placeId, entrines")
                }
                override fun onFailure(
                    call: Call<String>,
                    throwable: Throwable
                ) {
                    Log.d("mLogERR","$placeId, entrines")
                }
            }
        )
    }


    override fun deleteEntry(entryId: Int) {
        retrofitService.deleteEntry(entryId).enqueue(
            object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {}
                override fun onFailure(
                    call: Call<String>,
                    throwable: Throwable
                ) {}
            }
        )

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