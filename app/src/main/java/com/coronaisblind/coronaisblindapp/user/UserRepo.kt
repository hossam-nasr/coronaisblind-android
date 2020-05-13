package com.coronaisblind.coronaisblindapp.user

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserRepo(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {
    val currentUserResource: MutableLiveData<Resource<User?>> by lazy {
        MutableLiveData<Resource<User?>>(Resource.Success(null))
    }
    private var uid = auth.currentUser?.uid

    init {
        setAuthListener()
        setCurrentUser()
    }


    private fun setAuthListener() {
        auth.addAuthStateListener {
            uid = it.currentUser?.uid
            if (uid != null) {
                setUserListener(uid!!)
            }
        }
    }

    private fun setUserListener(uid: String) {
        db.collection("users").document(uid).addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w("FIREBASE", e)
            } else if (snapshot != null && snapshot.exists()) {
                currentUserResource.postValue(Resource.Success(snapshot.toObject(User::class.java)))
            }
        }
    }

    private fun setCurrentUser() {
        currentUserResource.postValue(Resource.Loading())
        if (uid != null) {
            setUserListener(uid!!)
        } else {
            currentUserResource.postValue(Resource.Success(null))
        }
    }
}