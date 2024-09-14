package com.example.profile.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.profile.data.data_structures.UserInfo
import com.example.profile.domain.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {

    fun userOut() {
        viewModelScope.launch {
            repository.userOut()
        }
    }

    fun getUserName(): String =
        repository.getUserName()
}

class ProfileViewModelFactory(private val profileRepository: ProfileRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ProfileViewModel(profileRepository) as T
}