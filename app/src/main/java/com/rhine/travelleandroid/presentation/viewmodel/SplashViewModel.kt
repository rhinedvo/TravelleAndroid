package com.rhine.travelleandroid.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhine.travelleandroid.domain.usecase.auth.GuestRegisterUseCase
import com.rhine.travelleandroid.domain.usecase.user.GetUserUseCase
import com.rhine.travelleandroid.domain.usecase.trips.GetTripsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashState {
    object Loading : SplashState()
    object NavigateToAuth : SplashState()
    object NavigateToMain : SplashState()
    object Error : SplashState()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val guestRegisterUseCase: GuestRegisterUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getTripsUseCase: GetTripsUseCase
) : ViewModel() {

    var splashState: SplashState = SplashState.Loading
        private set

    fun initializeApp(hasInternet: Boolean, hasToken: Boolean, isGuest: Boolean) {
        viewModelScope.launch {
            delay(1500)

            if (!hasInternet && !hasToken) {
                splashState = SplashState.NavigateToAuth
                return@launch
            }

            if (!hasToken) {
                guestRegisterUseCase()
                splashState = SplashState.NavigateToAuth
            } else {
                if (isGuest) {
                    splashState = SplashState.NavigateToMain
                } else {
                    try {
                        getUserUseCase()
                        getTripsUseCase()
                        splashState = SplashState.NavigateToMain
                    } catch (e: Exception) {
                        splashState = SplashState.Error
                    }
                }
            }
        }
    }
}