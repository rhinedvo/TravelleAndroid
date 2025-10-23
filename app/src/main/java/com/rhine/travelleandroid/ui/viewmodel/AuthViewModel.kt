package com.rhine.travelleandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhine.travelleandroid.domain.usecase.CheckEmailUseCase
import com.rhine.travelleandroid.domain.usecase.ForgotPasswordUseCase
import com.rhine.travelleandroid.domain.usecase.GuestRegisterUseCase
import com.rhine.travelleandroid.domain.usecase.LoginUseCase
import com.rhine.travelleandroid.domain.usecase.RegisterUseCase
import com.rhine.travelleandroid.domain.usecase.GetTokenStringUseCase
import com.rhine.travelleandroid.ui.state.UIState
import com.rhine.travelleandroid.ui.utils.ErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signInUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val guestRegisterUseCase: GuestRegisterUseCase,
    private val checkEmailUseCase: CheckEmailUseCase,
    private val getTokenStringUseCase: GetTokenStringUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState> get() = _state

    val errorMessage = MutableLiveData<String>()

    private val _emailExistsState = MutableLiveData<Boolean>()
    val emailExistsState: LiveData<Boolean> get() = _emailExistsState

    suspend fun getTokenString(): String? = getTokenStringUseCase()

    fun logIn(email: String, password: String) {
        _state.value = UIState.LOADING

        viewModelScope.launch {
            try {
                val result = signInUseCase(email, password)
                if (result.isSuccess) {
                    _state.value = UIState.SUCCESS
                } else {
                    val exception = result.exceptionOrNull()
                    val message = ErrorParser.parseApiError(exception)
                    errorMessage.value = message
                    _state.value = UIState.ERROR(message)
                }
            } catch (e: Exception) {
                errorMessage.value = ErrorParser.parseApiError(e)
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        _state.value = UIState.LOADING

        viewModelScope.launch {
            try {
                val result = registerUseCase(name, email, password)
                if (result.isSuccess) {
                    _state.value = UIState.SUCCESS
                } else {
                    val exception = result.exceptionOrNull()
                    val message = ErrorParser.parseApiError(exception)
                    errorMessage.value = message
                    _state.value = UIState.ERROR(message)
                }
            } catch (e: Exception) {
                errorMessage.value = ErrorParser.parseApiError(e)
            }
        }
    }

    fun forgotPassword(email: String) {
        _state.value = UIState.LOADING
        viewModelScope.launch {
            try {
                val result = forgotPasswordUseCase(email)
                if (result.isSuccess) {
                    _state.value = UIState.SUCCESS
                } else {
                    val exception = result.exceptionOrNull()
                    val message = ErrorParser.parseApiError(exception)
                    errorMessage.value = message
                    _state.value = UIState.ERROR(message)
                }
            } catch (e: Exception) {
                errorMessage.value = ErrorParser.parseApiError(e)
            }
        }
    }

    fun guestRegister() {
        _state.value = UIState.LOADING
        viewModelScope.launch {
            try {
                val result = guestRegisterUseCase()
                if (result.isSuccess) {
                    _state.value = UIState.SUCCESS
                } else {
                    val exception = result.exceptionOrNull()
                    val message = ErrorParser.parseApiError(exception)
                    errorMessage.value = message
                    _state.value = UIState.ERROR(message)
                }
            } catch (e: Exception) {
                errorMessage.value = ErrorParser.parseApiError(e)
            }
        }
    }

    fun checkEmail(email: String) {
        _state.value = UIState.LOADING

        viewModelScope.launch {
            try {
                val result = checkEmailUseCase(email)
                if (result.isSuccess) {
                    val emailExists = result.getOrDefault(false)
                    _emailExistsState.value = emailExists
                    _state.value = UIState.SUCCESS
                } else {
                    val exception = result.exceptionOrNull()
                    val message = ErrorParser.parseApiError(exception)
                    errorMessage.value = message
                    _state.value = UIState.ERROR(message)
                }
            } catch (e: Exception) {
                errorMessage.value = ErrorParser.parseApiError(e)
            }
        }
    }
}
