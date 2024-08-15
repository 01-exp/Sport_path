package com.example.auth.domain

import androidx.lifecycle.MutableLiveData

interface AuthRepository {
    fun regNewUser(
        userName: String,
        login: String,
        password: String,
        userIdMutableLiveData: MutableLiveData<Int>
    )

}

