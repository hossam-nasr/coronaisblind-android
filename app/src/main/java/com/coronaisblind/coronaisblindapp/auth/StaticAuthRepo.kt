package com.coronaisblind.coronaisblindapp.auth

import com.google.firebase.auth.FirebaseAuth

object StaticAuthRepo {
    private val uid: String = ""
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun getUserId(): String? {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            // TODO:  try logging in using shared preferences
        }
        return currentUser?.uid
    }
}