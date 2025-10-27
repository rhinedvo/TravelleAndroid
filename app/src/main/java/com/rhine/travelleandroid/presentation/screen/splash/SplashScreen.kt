package com.rhine.travelleandroid.presentation.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rhine.travelleandroid.R
import com.rhine.travelleandroid.presentation.viewmodel.SplashState
import com.rhine.travelleandroid.presentation.viewmodel.SplashViewModel
import com.rhine.travelleandroid.utils.isOnline

@Composable
fun SplashScreen(viewModel: SplashViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_long_logo),
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )
    }
}