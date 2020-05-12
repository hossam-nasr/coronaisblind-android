package com.coronaisblind.coronaisblindapp.auth

import androidx.lifecycle.MutableLiveData
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import java.util.concurrent.Executor

object LoginAuthRepo {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val uidResource: MutableLiveData<Resource<String?>> by lazy {
        val currentUser = auth.currentUser
        MutableLiveData<Resource<String?>>(Resource.Success(currentUser?.uid))
    }

    private val authListener = FirebaseAuth.AuthStateListener {
        val user = it.currentUser
        uidResource.postValue(Resource.Success(user?.uid))
    }

    init {
        val currentUser = auth.currentUser
        uidResource.value = Resource.Success(currentUser?.uid)
        auth.addAuthStateListener(authListener)
    }

    fun loginUser(email: String, password: String) {
        uidResource.value = Resource.Loading()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(AuthExecutor(), OnCompleteListener<AuthResult> { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser!!.uid
                    uidResource.postValue(Resource.Success(uid))
                } else {
                    uidResource.postValue(
                        Resource.Error(
                            task.exception?.message ?: "Authentication failed."
                        )
                    )
                }
            })

    }

    class AuthExecutor : Executor {
        override fun execute(command: Runnable) {
            Thread(command).run()
        }
    }
}