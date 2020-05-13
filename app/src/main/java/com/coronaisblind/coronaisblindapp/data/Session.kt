package com.coronaisblind.coronaisblindapp.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class Session(
    var id: String? = "",
    var startDate: Timestamp? = Timestamp.now(),
    var endDate: Timestamp? = Timestamp.now(),
    var number: Int? = 0,
    var activeDay: Int? = 0,
    var active: Boolean? = false,
    var done: Boolean? = false,
    var users: List<String>? = listOf()
) : Serializable