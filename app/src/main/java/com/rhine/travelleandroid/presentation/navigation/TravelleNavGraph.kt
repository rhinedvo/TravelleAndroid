package com.rhine.travelleandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rhine.travelleandroid.presentation.screen.splash.SplashScreen

@Composable
fun TravelleNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        composable(Routes.Splash) { SplashScreen(navController) }
        composable(Routes.Home) { HomeScreen() }
    }
}
