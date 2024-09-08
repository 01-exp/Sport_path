package com.example.profile.domain

import androidx.lifecycle.MutableLiveData
import com.example.profile.data.data_structures.UserInfo

interface ProfileRepository {
    fun getUserName(): String
    fun userOut()

}