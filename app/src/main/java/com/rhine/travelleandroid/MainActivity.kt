package com.rhine.travelleandroid

import android.net.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rhine.travelleandroid.presentation.navigation.TravelleNavGraph
import com.rhine.travelleandroid.presentation.screen.home.HomeScreen
import com.rhine.travelleandroid.ui.theme.TravelleTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private var isOnline = mutableStateOf(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupConnectivityMonitoring()

        setContent {
            TravelleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    TravelleNavGraph(
                        navController = navController,
                        isOnline = isOnline.value
                    )
                }
            }
        }
    }

    private fun setupConnectivityMonitoring() {
        connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOnline.value = true
            }

            override fun onLost(network: Network) {
                isOnline.value = false
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

@Composable
fun TravelleApp(isOnline: Boolean) {
    val navController = rememberNavController()

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
    }
}
