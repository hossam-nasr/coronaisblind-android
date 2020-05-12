package com.coronaisblind.coronaisblindapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.auth.SignupAuthViewModel
import com.coronaisblind.coronaisblindapp.data.Resource
import com.coronaisblind.coronaisblindapp.data.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    private val viewModel: SignupAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        populateFormFields()
        setFormListeners()

        btnSignup.setOnClickListener {
            if (validateForm()) {
                signup()
            }
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
                        showLoadingState(false)
                        finish()
                    }
                }
            }
        }
    }

    private fun populateFormFields() {
        etEmail.setText(viewModel.email)
        etPassword.setText(viewModel.password)
    }

    private fun setFormListeners() {
        setRadioGroupListeners()
        setCheckboxListeners()
    }

    private fun setRadioGroupListeners() {
        radioGroupGender.setOnCheckedChangeListener { _group, checkedId ->
            when (checkedId) {
                R.id.radioGenderMale -> viewModel.gender = User.MALE
                R.id.radioGenderFemale -> viewModel.gender = User.FEMALE
                R.id.radioGenderOther -> viewModel.gender = User.OTHER
            }
        }
    }

    private fun setCheckboxListeners() {
        cbLookingForMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.lookingFor.add(User.MALE)
            } else {
                viewModel.lookingFor.remove(User.MALE)
            }
        }

        cbLookingForFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.lookingFor.add(User.FEMALE)
            } else {
                viewModel.lookingFor.remove(User.FEMALE)
            }
        }

        cbLookingForOther.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.lookingFor.add(User.OTHER)
            } else {
                viewModel.lookingFor.remove(User.OTHER)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        if (loading) {
            tvError.visibility = View.GONE
            btnSignup.setBackgroundResource(R.drawable.action_button_main_large_loading)
            btnSignup.isEnabled = false
            btnLoginSpinner.visibility = View.VISIBLE
        } else {
            btnSignup.setBackgroundResource(R.drawable.action_button_main_large)
            btnSignup.isEnabled = true
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

    private fun validateForm(): Boolean {
        if (etEmail.text.isNullOrEmpty()) {
            etEmail.error = getString(R.string.error_required)
            etEmail.requestFocus()
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            etEmail.error = getString(R.string.error_email_format)
            etEmail.requestFocus()
            return false
        }
        if (etFirstName.text.isNullOrEmpty()) {
            etFirstName.error = getString(R.string.error_required)
            etFirstName.requestFocus()
            return false
        }
        if (etLastName.text.isNullOrEmpty()) {
            etLastName.error = getString(R.string.error_required)
            etLastName.requestFocus()
            return false
        }
        if (etVenmo.text.isNullOrEmpty()) {
            etVenmo.error = getString(R.string.error_required)
            etVenmo.requestFocus()
            return false
        }
        if (etPassword.text.isNullOrEmpty()) {
            etPassword.error = getString(R.string.error_required)
            etPasswordConfirm.requestFocus()
            return false
        }
        if (etPassword.text.toString().length < 8) {
            etPassword.error = getString(R.string.error_password_len)
            etPassword.requestFocus()
            return false
        }
        if (etPasswordConfirm.text.toString() != etPassword.text.toString()) {
            etPasswordConfirm.error = getString(R.string.error_password_match)
            etPasswordConfirm.requestFocus()
            return false
        }
        if (etFriendReferral.text.isNullOrEmpty()) {
            etFriendReferral.error = getString(R.string.error_required)
            etFriendReferral.requestFocus()
            return false
        }
        if (viewModel.gender == null) {
            radioGenderOther.error = getString(R.string.error_required)
            radioGroupGender.requestFocus()
            return false
        }
        if (viewModel.lookingFor.isNullOrEmpty()) {
            cbLookingForOther.error = getString(R.string.error_looking_for_len)
            cbGroupLookingFor.requestFocus()
            return false
        }
        if (!cbApprove.isChecked) {
            cbApprove.error = getString(R.string.error_required)
            cbApprove.requestFocus()
            return false
        }
        return true
    }

    private fun signup() {
        viewModel.signUp(
            etEmail.text.toString(), etPassword.text.toString(), User(
                email = etEmail.text.toString(),
                firstName = etFirstName.text.toString(),
                lastName = etLastName.text.toString(),
                venmo = etVenmo.text.toString(),
                friendEmails = etFriendReferral.text.toString(),
                gender = viewModel.gender,
                lookingFor = viewModel.lookingFor,
                id = null,
                flake = null,
                registration_date = null,
                callReviewsAboutMe = null,
                callReviewsByMe = null,
                calls = null,
                session = null
            )
        )
    }
}
