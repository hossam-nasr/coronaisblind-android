package com.coronaisblind.coronaisblindapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.fragment.*
import com.coronaisblind.coronaisblindapp.user.DashboardViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // navigation
        val homeFragment = HomeFragment()
        val historyFragment = HistoryFragment()
        val matchesFragment = MatchesFragment()
        val infoFragment = InfoFragment()
        val accountFragment = AccountFragment()

        switchToFragment(homeFragment)

        bottomNavBar.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.page_home -> switchToFragment(homeFragment)
                R.id.page_history -> switchToFragment(historyFragment)
                R.id.page_matches -> switchToFragment(matchesFragment)
                R.id.page_info -> switchToFragment(infoFragment)
                R.id.page_account -> switchToFragment(accountFragment)
            }
            true
        }


    }

    private fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }


}
