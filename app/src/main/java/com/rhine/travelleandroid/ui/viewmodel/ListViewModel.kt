package com.rhine.travelleandroid.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rhine.travelleandroid.usecase.ListUseCase
import com.rhine.travelleandroid.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class ListViewModel @Inject constructor(
    private val listUseCase: ListUseCase
) : ViewModel() {

    private val _state = MutableLiveData<UIState>()
    val state: LiveData<UIState> get() = _state

    fun getList() {
        _state.value = UIState.LOADING
        viewModelScope.launch {
            val result = listUseCase()
            _state.value = if (result.isSuccess) UIState.SUCCESS else UIState.ERROR(
                result.exceptionOrNull()?.message ?: "Unknown error"
            )
        }
    }
}


