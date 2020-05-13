package com.coronaisblind.coronaisblindapp.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userRepo = UserRepo(auth, db)
    val currentUserResource = userRepo.currentUserResource

    override fun onCleared() {
        super.onCleared()
    }
}