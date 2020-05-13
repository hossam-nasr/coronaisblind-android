package com.coronaisblind.coronaisblindapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.User
import com.coronaisblind.coronaisblindapp.user.DashboardViewModel
import kotlinx.android.synthetic.main.logo_and_welcome.*

class HomeFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private var currentUser: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel.currentUserResource.observe(this) {
            onUserUpdate(it)
        }

        updateUI()
        return view
    }

    private fun onUserUpdate(userResource: Resource<User?>) {
        when (userResource.status) {
//                Resource.Status.LOADING -> // TODO: showLoadingState(true)
//                    Resource.Status.ERROR -> {
//                // TODO: showError(it.message)
//                // TODO: showLoadingState(false)
//            }
            else -> {
                if (userResource.data != null) {
                    currentUser = userResource.data
                    updateUI()
                    // TODO: showLoadingState(false)
                }
            }
        }
    }

    private fun updateUI() {
        if (currentUser != null) {
            setViewData()
        }
        if (currentUser != null && !currentUser?.calls.isNullOrEmpty()) {
            loadFragment(CallsFragment())
        } else {
            // TODO: Load other fragments
        }
    }

    private fun loadFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.homeFragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }

    private fun setViewData() {
        tvWelcomeName.text = getString(R.string.welcome_name, currentUser?.firstName)
    }
}
