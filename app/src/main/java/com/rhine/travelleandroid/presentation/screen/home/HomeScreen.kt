package com.rhine.travelleandroid.presentation.screen.home

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rhine.travelleandroid.R
import com.rhine.travelleandroid.domain.model.Trip
import com.rhine.travelleandroid.presentation.viewmodel.MainNavigationEvent
import com.rhine.travelleandroid.presentation.viewmodel.MainUiState
import com.rhine.travelleandroid.presentation.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onNavigateToNewTrip: () -> Unit,
    onNavigateToSignIn: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToTripDetails: (Trip) -> Unit,
    onShowTripOptions: (Trip) -> Unit,
    onShowDraftSheet: (Trip) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()
    val context = LocalContext.current

    // Handle navigation events
    LaunchedEffect(navigationEvent) {
        when (val event = navigationEvent) {
            is MainNavigationEvent.NavigateToNewTrip -> {
                onNavigateToNewTrip()
                viewModel.onNavigationHandled()
            }
            is MainNavigationEvent.NavigateToSignIn -> {
                onNavigateToSignIn()
                viewModel.onNavigationHandled()
            }
            is MainNavigationEvent.NavigateToProfile -> {
                onNavigateToProfile()
                viewModel.onNavigationHandled()
            }
            is MainNavigationEvent.NavigateToTripDetails -> {
                onNavigateToTripDetails(event.trip)
                viewModel.onNavigationHandled()
            }
            is MainNavigationEvent.ShowTripOptions -> {
                onShowTripOptions(event.trip)
                viewModel.onNavigationHandled()
            }
            is MainNavigationEvent.ShowDraftBottomSheet -> {
                onShowDraftSheet(event.trip)
                viewModel.onNavigationHandled()
            }
            null -> { /* No event */ }
        }
    }

    // Auto-hide saved account message after 3 seconds
    LaunchedEffect(uiState.showSavedAccountMessage) {
        if (uiState.showSavedAccountMessage) {
            kotlinx.coroutines.delay(3000)
            viewModel.hideSavedAccountMessage()
        }
    }

    HomeContent(
        uiState = uiState,
        onStartTripClick = viewModel::onStartTripClicked,
        onSignInClick = viewModel::onSignInClicked,
        onProfileClick = viewModel::onProfileClicked,
        onTripClick = viewModel::onTripClicked,
        onTripOptionsClick = viewModel::onTripOptionsClicked,
        onDraftCardClick = viewModel::onDraftCardClicked,
        onSyncClick = viewModel::syncTripsFromServer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: MainUiState,
    onStartTripClick: () -> Unit,
    onSignInClick: () -> Unit,
    onProfileClick: () -> Unit,
    onTripClick: (Trip, Boolean) -> Unit,
    onTripOptionsClick: (Trip) -> Unit,
    onDraftCardClick: (Trip) -> Unit,
    onSyncClick: () -> Unit
) {
    val hasTrips = uiState.trips.isNotEmpty()
    val showEmptyState = uiState.isGuest || !hasTrips

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (showEmptyState) {
            EmptyStateContent(
                isGuest = uiState.isGuest,
                onStartTripClick = onStartTripClick,
                onSignInClick = onSignInClick,
                onProfileClick = onProfileClick
            )
        } else {
            TripsListContent(
                uiState = uiState,
                onStartTripClick = onStartTripClick,
                onSignInClick = onSignInClick,
                onProfileClick = onProfileClick,
                onTripClick = onTripClick,
                onTripOptionsClick = onTripOptionsClick,
                onDraftCardClick = onDraftCardClick,
                onSyncClick = onSyncClick
            )
        }
    }
}

@Composable
private fun EmptyStateContent(
    isGuest: Boolean,
    onStartTripClick: () -> Unit,
    onSignInClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top toolbar
        HomeTopBar(
            isGuest = isGuest,
            isOnline = true,
            showLogo = true,
            onSignInClick = onSignInClick,
            onProfileClick = onProfileClick
        )

        // Empty state content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 43.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Lottie animation would go here
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Your journey\nstarts here",
                fontSize = 31.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Create your first personalized itinerary — no sign-up needed",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.weight(1f))

            // Start planning button
            Button(
                onClick = onStartTripClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .height(54.dp),
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Start planning a trip",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TripsListContent(
    uiState: MainUiState,
    onStartTripClick: () -> Unit,
    onSignInClick: () -> Unit,
    onProfileClick: () -> Unit,
    onTripClick: (Trip, Boolean) -> Unit,
    onTripOptionsClick: (Trip) -> Unit,
    onDraftCardClick: (Trip) -> Unit,
    onSyncClick: () -> Unit
) {
    var isCollapsed by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Collapsing toolbar
            CollapsingToolbar(
                isCollapsed = isCollapsed,
                uiState = uiState,
                onSignInClick = onSignInClick,
                onProfileClick = onProfileClick,
                onSyncClick = onSyncClick,
                onCollapseChange = { isCollapsed = it }
            )

            // Trips list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 140.dp)
            ) {
                items(uiState.trips) { trip ->
                    TripItem(
                        trip = trip,
                        isOnline = uiState.isOnline,
                        onClick = { onTripClick(trip, false) },
                        onOptionsClick = { onTripOptionsClick(trip) },
                        onDraftClick = { onDraftCardClick(trip) }
                    )
                }
            }
        }

        // Floating action button with blur
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .align(Alignment.BottomCenter)
        ) {
            // Blur effect would go here
            FloatingActionButton(
                onClick = onStartTripClick,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(64.dp),
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus_24),
                    contentDescription = "Add trip",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun HomeTopBar(
    isGuest: Boolean,
    isOnline: Boolean,
    showLogo: Boolean,
    showSavedMessage: Boolean = false,
    onSignInClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(40.dp))

        // Center content
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when {
                showSavedMessage -> {
                    Surface(
                        color = Color(0xFF4CAF50).copy(alpha = 0.65f),
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cloud_check),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Saved to account",
                                color = Color.White,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                !isOnline -> {
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                        shape = MaterialTheme.shapes.extraLarge
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_cloud_off),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "You're offline",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
                showLogo -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_logo),
                        contentDescription = "Logo",
                        tint = Color.Unspecified
                    )
                }
            }
        }

        // Right side button
        if (isGuest) {
            Surface(
                onClick = onSignInClick,
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.extraLarge,
                shadowElevation = 2.dp
            ) {
                Text(
                    text = "Sign in",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 9.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            IconButton(onClick = onProfileClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile"
                )
            }
        }
    }
}

@Composable
private fun CollapsingToolbar(
    isCollapsed: Boolean,
    uiState: MainUiState,
    onSignInClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSyncClick: () -> Unit,
    onCollapseChange: (Boolean) -> Unit
) {
    Column {
        // Main toolbar
        HomeTopBar(
            isGuest = uiState.isGuest,
            isOnline = uiState.isOnline,
            showLogo = true,
            showSavedMessage = uiState.showSavedAccountMessage,
            onSignInClick = onSignInClick,
            onProfileClick = onProfileClick
        )

        // Title (hides on collapse)
        AnimatedVisibility(visible = !isCollapsed) {
            Text(
                text = "Your trips",
                fontSize = 31.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 18.dp, top = 26.dp, bottom = 26.dp)
            )
        }

        // Secondary toolbar (shows on collapse)
        AnimatedVisibility(visible = isCollapsed) {
            HomeTopBar(
                isGuest = uiState.isGuest,
                isOnline = uiState.isOnline,
                showLogo = false,
                onSignInClick = onSignInClick,
                onProfileClick = onProfileClick
            )
        }
    }
}

// You'll need to create TripItem composable separately
@Composable
private fun TripItem(
    trip: Trip,
    isOnline: Boolean,
    onClick: () -> Unit,
    onOptionsClick: () -> Unit,
    onDraftClick: () -> Unit
) {
    // Trip item implementation
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 8.dp)
    ) {
        // Trip content here
        Text(text = trip.title ?: trip.destination ?: "Trip")
    }
}

enum class TripStatus(val id: Int) {
    Generated(1),
    Draft(2),
    Customized(3),
    Deleted(4)
}