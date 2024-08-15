package com.example.splash.domain

import com.example.splash.data.SplashStorage

class SplashRepositoryImpl(val storage: SplashStorage):SplashRepository {
    override val userIsLogged: Boolean
        get() = storage.userIsLogged


}
