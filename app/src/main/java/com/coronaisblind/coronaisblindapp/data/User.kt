package com.coronaisblind.coronaisblindapp.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class User (
    var id: String? = "",
    var email: String? = "",
    var firstName: String? = "",
    var lastName: String? = "",
    var venmo: String? = "",
    var gender: String? = "'",
    var lookingFor: List<String>? = listOf(),
    var registration_date: Timestamp? = Timestamp.now(),
    var session: String? = "",
    var flake: Boolean? = null,
    var friendEmails: String? = "",
    var calls: List<String>? = listOf(),
    var callReviewsByMe: List<String>? = listOf(),
    var callReviewsAboutMe: List<String>? = listOf()
) : Serializable{

    companion object {
        val MALE = "male"
        val FEMALE = "female"
        val OTHER = "other"
    }
}