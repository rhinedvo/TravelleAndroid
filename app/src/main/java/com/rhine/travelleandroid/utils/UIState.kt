package com.rhine.travelleandroid.utils

sealed class UIState {
    object Loading : UIState()
    object NavigateToAuth : UIState()
    object NavigateToVerifyEmail : UIState()
    object NavigateToMain : UIState()
    data class Error(val message: String) : UIState()
}