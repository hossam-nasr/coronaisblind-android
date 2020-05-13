package com.coronaisblind.coronaisblindapp.session

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.Session
import com.google.firebase.firestore.FirebaseFirestore

class CurrentSessionRepo(private val db: FirebaseFirestore) {
    val currentSession: MutableLiveData<Resource<Session?>> by lazy {
        MutableLiveData<Resource<Session?>>(Resource.Success(null))
    }

    init {
        currentSession.postValue(Resource.Loading())
        db.collection("globalVars").document("sessionVars").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val currentSessionId = task.result!!["activeSession"].toString()
                db.collection("sessions").document(currentSessionId).get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("HELLO", "I'm here and result is ${it.result!!.toObject(Session::class.java)}")
                        currentSession.postValue(Resource.Success(it.result!!.toObject(Session::class.java)))
                    } else {
                        currentSession.postValue(
                            Resource.Error(
                                it.exception?.message
                                    ?: "Error retrieving session information from database"
                            )
                        )
                    }
                }
            } else {
                currentSession.postValue(
                    Resource.Error(
                        task.exception?.message ?: "Error retrieving information from database"
                    )
                )
            }
        }
    }
}