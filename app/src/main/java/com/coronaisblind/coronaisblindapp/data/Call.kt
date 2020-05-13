package com.coronaisblind.coronaisblindapp.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class Call(
    var id: String = "",
    var name: String = "",
    var day: Int = 0,
    var session: String = "",
    var time: Timestamp = Timestamp.now(),
    var url: String = "",
    var user1Id: String = "",
    var user2Id: String = "",
    var reveal: Boolean = false,
    var email: String = "",
    var lastName: String = ""
) : Serializable