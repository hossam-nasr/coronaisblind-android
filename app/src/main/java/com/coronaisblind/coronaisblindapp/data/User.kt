package com.coronaisblind.coronaisblindapp.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class User (
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val venmo: String,
    val gender: String,
    val lookingFor: List<String>,
    val registration_date: Timestamp,
    val session: String,
    val flake: Boolean?,
    val calls: List<String>?,
    val callReviewsByMe: List<String>?,
    val callReviewsAboutMe: List<String>?
) : Serializable