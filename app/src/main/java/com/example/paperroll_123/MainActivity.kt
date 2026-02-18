package com.example.paperroll_123

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.paperroll_123.data.PreferencesManager
import com.example.paperroll_123.ui.navigation.AppNavHost
import com.example.paperroll_123.ui.screens.main.MainViewModel
import com.example.paperroll_123.ui.theme.Paperroll123Theme
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {

    private val trustedUrl = "https://gcfinc.com/link.asp?a=DEMO_MD&b=ASDF2345&"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mantiene el splash hasta que Compose esté listo
        installSplashScreen()

        val preferences = PreferencesManager(this)

        setContent {
            Paperroll123Theme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {

                // Controla la animación de entrada
                var visible by remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    visible = true
                }

                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(animationSpec = tween(500)) +
                            scaleIn(
                                initialScale = 0.92f, // Zoom-in suave
                                animationSpec = tween(600)
                            )
                ) {
                    val navController = rememberNavController()
                    val mainViewModel: MainViewModel = viewModel()

                    AppNavHost(
                        navController = navController,
                        preferences = preferences,
                        trustedUrl = trustedUrl,
                        mainViewModel = mainViewModel
                    )
                }
            }
        }
    }
}
