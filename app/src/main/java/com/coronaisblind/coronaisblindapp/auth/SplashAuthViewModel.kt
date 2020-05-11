package com.coronaisblind.coronaisblindapp.auth

import androidx.lifecycle.ViewModel

class SplashAuthViewModel: ViewModel() {
    private val authRepo = StaticAuthRepo
    private var userId : String? = authRepo.getUserId()

    fun getUser(): String? {
        userId = authRepo.getUserId()
        return userId
    }
}