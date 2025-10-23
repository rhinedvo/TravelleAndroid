package com.rhine.travelleandroid.presentation.screen.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhine.travelleandroid.ui.main.MainActivity
import com.rhine.travelleandroid.ui.state.UIState
import com.rhine.travelleandroid.ui.theme.TravelleTheme
import com.rhine.travelleandroid.ui.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            TravelleTheme {
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
            viewModel.logIn("kramnu5351@gmail.com", pass)
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