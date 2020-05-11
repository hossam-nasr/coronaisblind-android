package com.coronaisblind.coronaisblindapp.activity

import android.content.Intent
import android.graphics.Matrix
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.coronaisblind.coronaisblindapp.R
import com.coronaisblind.coronaisblindapp.auth.AuthRepo
import com.coronaisblind.coronaisblindapp.auth.SplashAuthViewModel
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashAuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        adjustBackground()

        // route user to login or main screen
        routeUser()
    }

    private fun adjustBackground() {
        val matrix = Matrix()
        matrix.reset()
        matrix.postTranslate(-750f, 0f)
        ivBg.imageMatrix = matrix
    }

    private fun routeUser() {
        val user = splashViewModel.getUser()
        if (user != null) {
            // don't forget to pass user as intent arguments here
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }
}
