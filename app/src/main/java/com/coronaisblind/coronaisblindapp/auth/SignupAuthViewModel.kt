package com.coronaisblind.coronaisblindapp.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.User
import com.coronaisblind.coronaisblindapp.session.NextSessionRepo
import com.google.firebase.firestore.FirebaseFirestore

class SignupAuthViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val nextSessionRepo: NextSessionRepo = NextSessionRepo(db)
    private val authRepo : SignupAuthRepo = SignupAuthRepo(db, nextSessionRepo)

    var uidResource: LiveData<Resource<String?>> = authRepo.uidResource
    var email : String? = savedStateHandle["email"]
    var password: String? = savedStateHandle["password"]
    var gender: String? = null
    var lookingFor = mutableListOf<String>()


    fun signUp(email: String, password: String, user: User) {
        authRepo.signupUser(email, password, user)
    }

    override fun onCleared() {
        authRepo.clear()
        super.onCleared()
    }
}