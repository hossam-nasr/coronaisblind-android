package com.coronaisblind.coronaisblindapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.auth.LoginAuthViewModel
import com.coronaisblind.coronaisblindapp.data.Resource
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            if (validateForm()) {
                login()
            }
        }

        btnSignup.setOnClickListener {
            navigateToSignup()
        }

        tvMore.setOnClickListener {
            // TODO: implement rendering of the info fragment
        }


        viewModel.uidResource.observe(this) {
            when (it.status) {
                Resource.Status.LOADING -> showLoadingState(true)
                Resource.Status.ERROR -> {
                    showError(it.message)
                    showLoadingState(false)
                }
                else -> {
                    if (it.data != null && it.data.isNotEmpty()) {
                        val uid = it.data
                        intent = Intent(this, DashboardActivity::class.java)
                        intent.putExtra("UID", uid)
                        startActivity(intent)
                        finish()
                    }
                    showLoadingState(false)
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        if (etEmail.text.isNullOrEmpty()) {
            etEmail.error = getString(R.string.error_required)
            etEmail.requestFocus()
            return false
        }
        if (etPassword.text.isNullOrEmpty()) {
            etPassword.error = getString(R.string.error_required)
            etPassword.requestFocus()
            return false
        }
        if (etPassword.text.toString().length < 8) {
            etPassword.error = getString(R.string.error_password_len)
            etPassword.requestFocus()
            return false
        }
        return true
    }

    private fun login() {
        // TODO: Data validation
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()
        viewModel.loginUser(email, password)
    }

    private fun showLoadingState(loading: Boolean) {
        if (loading) {
            tvError.visibility = View.GONE
            btnLogin.setBackgroundResource(R.drawable.action_button_main_large_loading)
            btnLogin.isEnabled = false
            btnLoginSpinner.visibility = View.VISIBLE
        } else {
            btnLogin.setBackgroundResource(R.drawable.action_button_main_large)
            btnLogin.isEnabled = true
            btnLoginSpinner.visibility = View.GONE
        }
    }

    private fun showError(message: String?) {
        if (message != null) {
            tvError.text = message
            tvError.visibility = View.VISIBLE
            tvError.requestFocus()
        }
    }

    private fun navigateToSignup() {
        val intent = Intent(this, SignupActivity::class.java)
        intent.putExtra("EMAIL", etEmail.text.toString())
        intent.putExtra("PASSWORD", etPassword.text.toString())
        startActivity(intent)
    }



}
