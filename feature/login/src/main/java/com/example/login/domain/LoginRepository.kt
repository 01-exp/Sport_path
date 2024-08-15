package com.example.login.domain

import androidx.lifecycle.MutableLiveData
import com.example.login.data.data_structures.UserInfo

interface LoginRepository {
    fun loginUser(
        login: String,
        password: String,
        loginCodeMutableLiveData: MutableLiveData<UserInfo>
    )

}

