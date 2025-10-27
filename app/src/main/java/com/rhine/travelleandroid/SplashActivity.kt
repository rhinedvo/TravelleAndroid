package com.rhine.travelleandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.rhine.travelleandroid.presentation.screen.splash.SplashScreen
import com.rhine.travelleandroid.presentation.viewmodel.SplashState
import com.rhine.travelleandroid.presentation.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashScreen(viewModel = viewModel)
        }
        viewModel.initializeApp(
            hasInternet = true,
            hasToken = false,
            isGuest = false
        )
        observeState()
    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.splashStateFlow.collect { state ->
                    when (state) {
                        SplashState.NavigateToAuth -> {
//                            startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
//                            finish()
                        }
                        SplashState.NavigateToMain -> {
                            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                            finish()
                        }
                        else -> Unit
                    }
                }
            }
        }
    }
}