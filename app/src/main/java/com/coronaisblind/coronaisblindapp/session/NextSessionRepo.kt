package com.coronaisblind.coronaisblindapp.session

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coronaisblind.coronaisblindapp.data.Resource
import com.google.firebase.firestore.FirebaseFirestore

class NextSessionRepo(private val db: FirebaseFirestore) {
    val nextSessionId: MutableLiveData<Resource<String?>> by lazy {
        MutableLiveData<Resource<String?>>(Resource.Success(null))
    }

    init {
        db.collection("globalVars").document("sessionVars").get().addOnCompleteListener {
            if (it.isSuccessful) {
                nextSessionId.postValue(Resource.Success(it.result!!["nextSession"].toString()))
            } else {
                nextSessionId.postValue(
                    Resource.Error(
                        it.exception?.message ?: "Error retrieving session document"
                    )
                )
            }
        }

        db.collection("globalVars").document("sessionVars").addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("FIREBASE", e)
            } else if (snapshot != null && snapshot.exists()) {
                nextSessionId.postValue(Resource.Success(snapshot.data!!["nextSession"].toString()))
            }
        }
    }

}