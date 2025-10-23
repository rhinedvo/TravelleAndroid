package com.rhine.travelleandroid.ui.state

sealed class UIState {
    object LOADING : UIState()
    object SUCCESS : UIState()
    data class ERROR(val message: String) : UIState()
}
