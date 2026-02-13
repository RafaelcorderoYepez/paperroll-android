package com.example.paperroll_123.ui.screens.main

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paperroll_123.domain.errors.ErrorType
import com.example.paperroll_123.domain.errors.mapExceptionToError
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var errorState by mutableStateOf<ErrorType?>(null)
        private set

    fun loadData() {
        viewModelScope.launch {
            try {
                // Call to a service or repository
                // repository.getData()
            } catch (e: Exception) {
                errorState = mapExceptionToError(e)
            }
        }
    }

    fun onWebError(type: ErrorType) {
        errorState = type
    }

    fun clearError() {
        errorState = null
    }
}
