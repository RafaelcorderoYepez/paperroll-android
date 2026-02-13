package com.example.paperroll_123.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.paperroll_123.data.PreferencesManager
import com.example.paperroll_123.ui.screens.disclaimer.DisclaimerScreen
import com.example.paperroll_123.ui.screens.provider.ProviderScreen
import kotlinx.coroutines.launch

/**
 * Main navigation graph for the application.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    preferences: PreferencesManager,
    trustedUrl: String
) {
    val scope = rememberCoroutineScope()
    val accepted by preferences.disclaimerAccepted.collectAsState(initial = false)

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
            ProviderScreen(trustedUrl = trustedUrl)
        }
    }
}
