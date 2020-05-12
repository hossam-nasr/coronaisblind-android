package com.coronaisblind.coronaisblindapp.data

import java.io.Serializable

data class sessionVars (
    var activeSession: String?,
    var activeSessionNum: String?,
    var nextSession: String?,
    var pastSessions: List<String>?
) : Serializable