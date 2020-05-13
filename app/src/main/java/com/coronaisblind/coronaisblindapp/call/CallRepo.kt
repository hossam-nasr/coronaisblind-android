package com.coronaisblind.coronaisblindapp.call

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.coronaisblind.coronaisblindapp.data.Call
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CallRepo(
    private val db: FirebaseFirestore,
    private val userResource: LiveData<Resource<User?>>,
    private val viewModelScope: CoroutineScope
) {
    private var user = userResource.value?.data
    private val userObserver = Observer<Resource<User?>> {
        if (it.status == Resource.Status.SUCCESS) {
            user = it.data
            updateCallList()
        } else {
            user = null
        }
    }

    val callListResource: MutableLiveData<Resource<MutableList<Call?>?>> by lazy {
        MutableLiveData<Resource<MutableList<Call?>?>>(Resource.Success(null))
    }

    init {
        setUserListener()
        updateCallList()
    }

    private fun setUserListener() {
        userResource.observeForever(userObserver)
    }


    private suspend fun getUserFromId(userId: String): User? {
        val userDoc = db.collection("users").document(userId).get().await()
        return userDoc.toObject(User::class.java)
    }
    private suspend fun getCallFromId(callId: String): Call? {
        val callData = db.collection("calls").document(callId).get().await()
        val call = callData.toObject(Call::class.java) ?: return null
        val otherUserId: String
        if (user?.id!! == call.user1Id) {
            otherUserId = call.user2Id
        } else if (user?.id!! == call.user2Id) {
            otherUserId = call.user1Id
        } else {
            return null
        }
        val otherUser = getUserFromId(otherUserId)
        call.name = otherUser?.firstName!!
        return call
    }

    private suspend fun loadCallList(): MutableList<Call?> {
        if (!user?.calls.isNullOrEmpty()) {
            val list = mutableListOf<Call?>()
            val deferred = user?.calls?.map {
                viewModelScope.async {
                    list.add(getCallFromId(it))
                }
            }?.awaitAll()
            return list
        } else {
            return mutableListOf()
        }
    }

    private fun updateCallList() {
        callListResource.postValue(Resource.Loading())
        viewModelScope.launch {
            try {
                callListResource.postValue(Resource.Success(loadCallList()))
            } catch (exception: Exception) {
                callListResource.postValue(Resource.Error(exception.message!!))
            }
        }


    }


    fun clear() {
        userResource.removeObserver(userObserver)
    }
}