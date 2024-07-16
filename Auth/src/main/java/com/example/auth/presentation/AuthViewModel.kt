package com.example.auth.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.AuthRepository
import com.example.sport_path.data_structures.UserInfo
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {

    private val userIdMutable: MutableLiveData<Int> = MutableLiveData()
    val userId: LiveData<Int> = userIdMutable

    private val loginCodeMutable: MutableLiveData<UserInfo> = MutableLiveData()
    val loginCode: LiveData<UserInfo> = loginCodeMutable
    fun userOut() {
        viewModelScope.launch {
            repository.userOut(loginCodeMutable)
        }
    }
    fun regNewUser(userName: String, login: String, password: String) {
        viewModelScope.launch {
            repository.getNewUser(userName, login, password, userIdMutable)
        }
    }
    fun getUserName(): String =
        repository.getUserName()


    fun isUserLogged(): Boolean =
        repository.isUserLogged()


    fun loginUser(login: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(login, password, loginCodeMutable)
        }
    }
}
class AuthViewModelFactory(private val authRepository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AuthViewModel(authRepository) as T
}