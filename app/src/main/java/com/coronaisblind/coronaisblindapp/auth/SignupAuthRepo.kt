package com.coronaisblind.coronaisblindapp.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.User
import com.coronaisblind.coronaisblindapp.session.NextSessionRepo
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Timestamp
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executor

class SignupAuthRepo(
    private val db: FirebaseFirestore,
    private val nextSessionRepo: NextSessionRepo
) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var nextSessionId: String? = null

    val uidResource: MutableLiveData<Resource<String?>> by lazy {
        val currentUser = auth.currentUser
        MutableLiveData<Resource<String?>>(Resource.Success(currentUser?.uid))
    }

    private val nextSessionObserver = Observer<Resource<String?>> {
        if (it.status == Resource.Status.SUCCESS) {
            nextSessionId = it.data
        }
    }


    init {
        val currentUser = auth.currentUser
        uidResource.value = Resource.Success(currentUser?.uid)
        nextSessionRepo.nextSessionId.observeForever(nextSessionObserver)
    }

    fun signupUser(email: String, password: String, user: User) {
        uidResource.value = Resource.Loading()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(AuthExecutor(),
                OnCompleteListener<AuthResult> { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser!!.uid
                        postUserData(uid, user)
                    } else {
                        uidResource.postValue(
                            Resource.Error(task.exception?.message ?: "Sign up failed.")
                        )
                    }
                })
    }

    private fun postUserData(uid: String, user: User) {
        user.id = uid
        user.session = nextSessionId
        user.registration_date = Timestamp.now()
        db.collection("users").document(uid).set(user).addOnCompleteListener {
            if (it.isSuccessful) {
                uidResource.postValue(Resource.Success(uid))
            } else {
                uidResource.postValue(
                    Resource.Error(
                        it.exception?.message ?: "Posting user to database failed"
                    )
                )
            }
        }
    }

    class AuthExecutor : Executor {
        override fun execute(command: Runnable) {
            Thread(command).run()
        }
    }

    fun clear() {
        nextSessionRepo.nextSessionId.removeObserver(nextSessionObserver)
    }
}