package com.coronaisblind.coronaisblindapp.user

import androidx.lifecycle.*
import com.coronaisblind.coronaisblindapp.call.CallRepo
import com.coronaisblind.coronaisblindapp.data.Call
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.session.CurrentSessionRepo
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DashboardViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val currentSessionRepo = CurrentSessionRepo(db)
    private val userRepo = UserRepo(auth, db)
    private val callRepo = CallRepo(db, userRepo.currentUserResource, viewModelScope)
    val currentSessionResource = currentSessionRepo.currentSession
    val currentUserResource = userRepo.currentUserResource
    val upcomingCallsResource: LiveData<Resource<List<Call>>> =
        Transformations.map(callRepo.callListResource) { resource ->
            if (resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data.isNullOrEmpty()) {
                resource as Resource<List<Call>>
            } else {
                val rawList = resource.data
                Resource.Success(rawList.filterNotNull().filter {
                    //  TODO: Make sure you only filter them out if the call duration has also passed
                    it.time >= Timestamp.now()
                }.sortedBy {
                    it.time
                })
            }
        }
    val previousCallsResource: LiveData<Resource<List<Call>>> =
        Transformations.map(callRepo.callListResource) { resource ->
            if (resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data.isNullOrEmpty()) {
                resource as Resource<List<Call>>
            } else {
                val rawList = resource.data
                Resource.Success(rawList.filterNotNull().filter {
                    //  TODO: Make sure you only filter them out if the call duration has also passed
                    it.time < Timestamp.now()
                }.sortedByDescending {
                    it.time
                })
            }
        }
    val matchesResource: LiveData<Resource<List<Call>>> =
        Transformations.map(callRepo.callListResource) { resource ->
            if (resource.status == Resource.Status.ERROR || resource.status == Resource.Status.LOADING || resource.data.isNullOrEmpty()) {
                resource as Resource<List<Call>>
            } else {
                val rawList = resource.data
                Resource.Success(rawList.filterNotNull().filter {
                    //  TODO: Make sure you only filter them out if the call duration has also passed
                    it.reveal
                }.sortedByDescending {
                    it.time
                })
            }
        }

    override fun onCleared() {
        callRepo.clear()
        super.onCleared()
    }
}