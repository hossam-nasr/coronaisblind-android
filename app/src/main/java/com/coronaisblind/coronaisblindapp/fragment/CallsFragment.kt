package com.coronaisblind.coronaisblindapp.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.adapter.CallListAdapter
import com.coronaisblind.coronaisblindapp.data.Call
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.User
import com.coronaisblind.coronaisblindapp.user.DashboardViewModel
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.fragment_calls.*

class CallsFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calls, container, false)

        viewModel.currentUserResource.observe(this) {
            onUserUpdate(it)
        }

        initRecyclerView(view)
        updateUI()

        return view
    }

    private fun onUserUpdate(userResource: Resource<User?>) {
        if (userResource.status == Resource.Status.SUCCESS && userResource.data?.calls.isNullOrEmpty()) {
            currentUser = userResource.data
            updateUI()
        }
    }

    private fun updateUI() {

    }

    private fun initRecyclerView(rootView: View) {
        val callList = listOf(
            Call(name = "Hossam", time = Timestamp.now(), url = ""),
            Call(name = "Gayar", time = Timestamp.now(), url = ""),
            Call(name = "Madeline", time = Timestamp.now(), url = "")
        )

        val adapter = CallListAdapter(activity as Context, callList)
        Log.d("HELLO", "Adapter is $adapter")
        val rvUpcomingCalls = rootView.findViewById<RecyclerView>(R.id.rvUpcomingCalls)
        rvUpcomingCalls?.layoutManager = LinearLayoutManager(activity)
        rvUpcomingCalls?.adapter = adapter
    }
}