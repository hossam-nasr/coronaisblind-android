package com.coronaisblind.coronaisblindapp.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.coronaisblind.coronaisblindapp.data.Resource

class LoginAuthViewModel : ViewModel() {
    private val authRepo = LoginAuthRepo
    var uidResource: LiveData<Resource<String?>> = authRepo.uidResource


    fun loginUser(email: String, password: String) {
        authRepo.loginUser(email, password)
    }
}