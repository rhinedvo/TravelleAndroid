package com.rhine.travelleandroid.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.livedata.observeAsState
import com.kodetechnologies.luxeaiandroid.ui.theme.LuxeAiAndroidTheme
import com.rhine.travelleandroid.ui.state.UIState
import com.rhine.travelleandroid.ui.viewmodel.AuthViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import android.app.Activity
import android.content.Intent
import com.rhine.travelleandroid.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            LuxeAiAndroidTheme {
                SplashScreen(
                )
            }
        }
    }

    @Composable
    fun SplashScreen(
        viewModel: AuthViewModel = hiltViewModel()
    ) {
        val context = LocalContext.current
        val state by viewModel.state.observeAsState(initial = UIState.LOADING)

        LaunchedEffect(Unit) {
            // Test login request to verify flow
            val pass = "Vivino#33"
            viewModel.logIn("shkelet1987@gmail.com", pass)
        }

        when (state) {
            is UIState.LOADING -> Unit
            is UIState.SUCCESS -> {
                LaunchedEffect("navigate") {
                    val token = viewModel.getTokenString()
                    // Navigate based on token; if no AuthActivity exists, fall back to MainActivity
                    context.startActivity(
                        Intent(
                            context,
                            MainActivity::class.java
                        )
                    )
                    (context as? Activity)?.finish()
                }
            }
            else -> Unit
        }
    }
}