package com.coronaisblind.coronaisblindapp.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class User (
    var id: String?,
    var email: String?,
    var firstName: String?,
    var lastName: String?,
    var venmo: String?,
    var gender: String?,
    var lookingFor: List<String>?,
    var registration_date: Timestamp?,
    var session: String?,
    var flake: Boolean?,
    var friendEmails: String?,
    var calls: List<String>?,
    var callReviewsByMe: List<String>?,
    var callReviewsAboutMe: List<String>?
) : Serializable{

    companion object {
        val MALE = "male"
        val FEMALE = "female"
        val OTHER = "other"
    }
}