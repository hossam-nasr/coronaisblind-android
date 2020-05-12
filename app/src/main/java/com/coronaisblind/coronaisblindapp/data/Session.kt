package com.coronaisblind.coronaisblindapp.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class Session(
    var id: String?,
    var startDate: Timestamp?,
    var endDate: Timestamp?,
    var number: Int?,
    var activeDay: Int?,
    var active: Boolean?,
    var done: Boolean?,
    var users: List<String>?
) : Serializable