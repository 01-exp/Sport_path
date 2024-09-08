package com.example.profile.domain

import androidx.lifecycle.MutableLiveData
import com.example.profile.data.ProfileStorage
import com.example.profile.data.data_structures.UserInfo

class ProfileRepositoryImpl(private val storage: ProfileStorage) :ProfileRepository{

    override fun getUserName(): String =
        storage.getUserName()
    override fun userOut() {
        storage.clearUserData()
    }
}