package com.example.paperroll_123.ui.screens.main

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.paperroll_123.domain.errors.ErrorType
import com.example.paperroll_123.ui.PaperrollProviderWebView
import com.example.paperroll_123.ui.components.ErrorModal

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel()
) {
    val error: ErrorType? = viewModel.errorState

    // Contenido principal: el WebView
    PaperrollProviderWebView(
        url = "https://gcfinc.com", // o la URL que uses
        onError = { type ->
            viewModel.onWebError(type)
        }
    )

    // Modal de error encima del contenido
    if (error != null) {
        ErrorModal(
            error = error,
            onRetry = {
                viewModel.clearError()
                viewModel.loadData() // si quieres recargar datos
            },
            onHome = {
                viewModel.clearError()
            },
            onDismiss = {
                viewModel.clearError()
            }
        )
    }
}
