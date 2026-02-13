package com.example.paperroll_123

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.paperroll_123.ui.theme.Paperroll123Theme
import androidx.navigation.compose.rememberNavController
import com.example.paperroll_123.data.PreferencesManager
import com.example.paperroll_123.ui.navigation.AppNavHost

class MainActivity : ComponentActivity() {

    private val trustedUrl = "https://gcfinc.com/link.asp?a=DEMO_MD&b=ASDF2345&"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        val preferences = PreferencesManager(this)

        setContent {
            Paperroll123Theme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false
            ) {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    preferences = preferences,
                    trustedUrl = trustedUrl
                )
            }
        }
    }
}
