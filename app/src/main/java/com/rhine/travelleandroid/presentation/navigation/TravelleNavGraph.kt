package com.rhine.travelleandroid.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rhine.travelleandroid.presentation.screen.home.HomeScreen
import com.rhine.travelleandroid.presentation.screen.splash.SplashScreen

@Composable
fun TravelleNavGraph(
    navController: NavHostController,
    isOnline: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToNewTrip = { navController.navigate("new_trip") },
                onNavigateToSignIn = { navController.navigate("auth") },
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToTripDetails = { trip ->
                    navController.navigate("trip_details/${trip.id}")
                },
                onShowTripOptions = { /* TODO */ },
                onShowDraftSheet = { /* TODO */ }
            )
        }

        // приклад інших екранів:
        composable("new_trip") { /* ... */ }
        composable("auth") { /* ... */ }
        composable("profile") { /* ... */ }
        composable("trip_details/{tripId}") { backStackEntry ->
            val tripId = backStackEntry.arguments?.getString("tripId")
            // TODO: передати tripId у TripDetailsScreen
        }
    }
}