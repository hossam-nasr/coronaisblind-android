package com.coronaisblind.coronaisblindapp.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class DashboardViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    var uid : String? = savedStateHandle["UID"]

    override fun onCleared() {
        super.onCleared()
    }
}