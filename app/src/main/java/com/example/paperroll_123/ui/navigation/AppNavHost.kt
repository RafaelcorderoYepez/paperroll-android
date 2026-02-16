package com.example.paperroll_123.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.paperroll_123.data.PreferencesManager
import com.example.paperroll_123.ui.screens.disclaimer.DisclaimerScreen
import com.example.paperroll_123.ui.screens.provider.ProviderScreen
import com.example.paperroll_123.ui.screens.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    navController: NavHostController,
    preferences: PreferencesManager,
    trustedUrl: String,
    mainViewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()

    // null = todavía cargando DataStore
    val acceptedState = preferences.disclaimerAccepted.collectAsState(initial = null)

    // Mientras DataStore carga, no mostramos nada (o podrías mostrar tu Splash)
    if (acceptedState.value == null) {
        return
    }

    val accepted = acceptedState.value == true

    NavHost(
        navController = navController,
        startDestination = if (accepted) AppRoutes.PROVIDER else AppRoutes.DISCLAIMER
    ) {

        composable(AppRoutes.DISCLAIMER) {
            DisclaimerScreen(
                onAccepted = {
                    scope.launch {
                        preferences.setDisclaimerAccepted(true)
                        navController.navigate(AppRoutes.PROVIDER) {
                            popUpTo(AppRoutes.DISCLAIMER) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(AppRoutes.PROVIDER) {
            ProviderScreen(
                trustedUrl = trustedUrl,
                viewModel = mainViewModel
            )
        }
    }
}
