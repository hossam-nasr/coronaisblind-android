package com.coronaisblind.coronaisblindapp.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.coronaisblind.coronaisblindapp.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        if (true) {
            loadFragment(CallsFragment())
        } else {

        }
        return view
    }

    private fun loadFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.apply {
            replace(R.id.homeFragmentContainer, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
