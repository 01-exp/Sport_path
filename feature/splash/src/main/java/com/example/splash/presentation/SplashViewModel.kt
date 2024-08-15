package com.example.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.splash.domain.SplashRepository

class SplashViewModel(private val repository: SplashRepository) : ViewModel(){
    fun isUserLogged(): Boolean = repository.userIsLogged
}
class SplashViewModelFactory(private val repository: SplashRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        SplashViewModel(repository) as T
}