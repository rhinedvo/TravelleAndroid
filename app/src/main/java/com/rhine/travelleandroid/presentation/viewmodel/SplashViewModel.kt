package com.rhine.travelleandroid.presentation.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhine.travelleandroid.domain.usecase.GetUserTokenUseCase
import com.rhine.travelleandroid.domain.usecase.RegisterGuestUseCase
import com.rhine.travelleandroid.domain.usecase.GetUserUseCase
import com.rhine.travelleandroid.domain.usecase.GetAllTripsUseCase
import com.rhine.travelleandroid.domain.usecase.GetListUseCase
import com.rhine.travelleandroid.domain.model.User
import com.rhine.travelleandroid.domain.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val registerGuestUseCase: RegisterGuestUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getAllTripsUseCase: GetAllTripsUseCase,
    private val getListUseCase: GetListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    fun initialize(isOnline: Boolean) {
        viewModelScope.launch {
            if (!isOnline) {
                val token = getUserTokenUseCase()
                if (token != null) _uiState.value = UIState.NavigateToMain
                else _uiState.value = UIState.NavigateToAuth
                return@launch
            }

            // Завантажуємо початкові дані
            getListUseCase().onSuccess {
                val token = getUserTokenUseCase()
                if (token == null) {
                    registerGuestUseCase()
                    _uiState.value = UIState.NavigateToAuth
                } else {
                    val user = getUserUseCase()
                    handleUser(user)
                }
            }.onFailure {
                _uiState.value = UIState.Error("Failed to load initial list")
            }
        }
    }

    private suspend fun handleUser(user: User?) {
        if (user == null) {
            _uiState.value = UIState.NavigateToAuth
        } else if (!user.isEmailVerified) {
            _uiState.value = UIState.NavigateToVerifyEmail
        } else {
            getAllTripsUseCase()
            _uiState.value = UIState.NavigateToMain
        }
    }
}