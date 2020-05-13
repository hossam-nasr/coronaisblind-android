package com.coronaisblind.coronaisblindapp.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.adapter.MatchesAdapter
import com.coronaisblind.coronaisblindapp.data.Call
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.user.DashboardViewModel

class MatchesFragment : Fragment() {

    private val viewModel: DashboardViewModel by activityViewModels()
    lateinit var rootView: View
    lateinit var matchesAdapter: MatchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_matches, container, false)

        initRecyclerView()

        viewModel.matchesResource.observe(this) {
            if (it.status == Resource.Status.SUCCESS && !it.data.isNullOrEmpty()) {
                updateUI(it.data)
            }
        }

        return rootView
    }

    private fun initRecyclerView() {
        matchesAdapter = MatchesAdapter(activity as Context, listOf())
        val rvUpcomingCalls = rootView.findViewById<RecyclerView>(R.id.rvMatches)
        rvUpcomingCalls?.layoutManager = LinearLayoutManager(activity)
        rvUpcomingCalls?.adapter = matchesAdapter
    }

    private fun updateUI(list: List<Call>) {
        matchesAdapter.updateList(list)
    }
}
