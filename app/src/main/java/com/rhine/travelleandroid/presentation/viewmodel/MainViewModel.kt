package com.rhine.travelleandroid.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhine.travelleandroid.data.remote.dto.TripDTO
import com.rhine.travelleandroid.domain.model.Trip
import com.rhine.travelleandroid.domain.repository.LocalUserRepository
import com.rhine.travelleandroid.domain.usecase.trips.GetTripsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val trips: List<Trip> = emptyList(),
    val isLoading: Boolean = false,
    val isOnline: Boolean = true,
    val isGuest: Boolean = true,
    val showSavedAccountMessage: Boolean = false,
    val error: String? = null
)

sealed class MainNavigationEvent {
    object NavigateToNewTrip : MainNavigationEvent()
    object NavigateToSignIn : MainNavigationEvent()
    object NavigateToProfile : MainNavigationEvent()
    data class NavigateToTripDetails(val trip: Trip) : MainNavigationEvent()
    data class ShowTripOptions(val trip: Trip) : MainNavigationEvent()
    data class ShowDraftBottomSheet(val trip: Trip) : MainNavigationEvent()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTripsUseCase: GetTripsUseCase,
    private val localUserRepository: LocalUserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _navigationEvent = MutableStateFlow<MainNavigationEvent?>(null)
    val navigationEvent: StateFlow<MainNavigationEvent?> = _navigationEvent.asStateFlow()

    init {
        observeUser()
        loadTrips()
    }

    private fun observeUser() {
        viewModelScope.launch {
            localUserRepository.getUserFlow().collect { user ->
                _uiState.update { it.copy(isGuest = user?.isGuest ?: true) }
            }
        }
    }
    fun loadTrips() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val trips = getTripsUseCase().getOrElse { emptyList() }
                _uiState.update {
                    it.copy(
                        trips = trips,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun onStartTripClicked() {
        _navigationEvent.value = MainNavigationEvent.NavigateToNewTrip
    }

    fun onSignInClicked() {
        _navigationEvent.value = MainNavigationEvent.NavigateToSignIn
    }

    fun onProfileClicked() {
        _navigationEvent.value = MainNavigationEvent.NavigateToProfile
    }

    fun onTripClicked(trip: Trip, isDraft: Boolean) {
        _navigationEvent.value = if (isDraft) {
            MainNavigationEvent.ShowDraftBottomSheet(trip)
        } else {
            MainNavigationEvent.NavigateToTripDetails(trip)
        }
    }

    fun onTripOptionsClicked(trip: Trip) {
        _navigationEvent.value = MainNavigationEvent.ShowTripOptions(trip)
    }

    fun onDraftCardClicked(trip: Trip) {
        _navigationEvent.value = MainNavigationEvent.ShowDraftBottomSheet(trip)
    }

    fun updateOnlineStatus(isOnline: Boolean) {
        val wasOffline = !_uiState.value.isOnline
        _uiState.update {
            it.copy(
                isOnline = isOnline,
                showSavedAccountMessage = wasOffline && isOnline
            )
        }
    }

    fun updateUserStatus(isGuest: Boolean) {
        _uiState.update { it.copy(isGuest = isGuest) }
    }

    fun hideSavedAccountMessage() {
        _uiState.update { it.copy(showSavedAccountMessage = false) }
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }

    fun syncTripsFromServer() {
        if (_uiState.value.isOnline) {
            loadTrips()
        }
    }
}