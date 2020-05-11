package com.coronaisblind.coronaisblindapp.data

import com.google.firebase.Timestamp
import java.io.Serializable

data class Session(
    val id: String,
    val startDate: Timestamp,
    val endDate: Timestamp,
    val number: Int,
    val activeDay: Int,
    val active: Boolean?,
    val done: Boolean?,
    val users: List<String>?
) : Serializable