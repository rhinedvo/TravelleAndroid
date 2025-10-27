package com.rhine.travelleandroid.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhine.travelleandroid.domain.usecase.auth.GuestRegisterUseCase
import com.rhine.travelleandroid.domain.usecase.user.GetUserUseCase
import com.rhine.travelleandroid.domain.usecase.trips.GetTripsUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _splashStateFlow = MutableStateFlow<SplashState>(SplashState.Loading)
    val splashStateFlow = _splashStateFlow.asStateFlow()

    fun initializeApp(hasInternet: Boolean, hasToken: Boolean, isGuest: Boolean) {
        viewModelScope.launch {
            delay(1500)

            when {
                !hasInternet && !hasToken -> {
                    _splashStateFlow.value = SplashState.NavigateToAuth
                }

                !hasToken -> {
                    guestRegisterUseCase()
                    _splashStateFlow.value = SplashState.NavigateToAuth
                }

                isGuest -> {
                    _splashStateFlow.value = SplashState.NavigateToMain
                }

                else -> {
                    try {
                        getUserUseCase()
                        getTripsUseCase()
                        _splashStateFlow.value = SplashState.NavigateToMain
                    } catch (e: Exception) {
                        _splashStateFlow.value = SplashState.Error
                    }
                }
            }
        }
    }
}