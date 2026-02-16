package com.example.paperroll_123.ui.screens.provider

import android.webkit.WebView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.paperroll_123.domain.errors.ErrorType
import com.example.paperroll_123.ui.PaperrollProviderWebView
import com.example.paperroll_123.ui.components.BottomBar
import com.example.paperroll_123.ui.components.ErrorModal
import com.example.paperroll_123.ui.components.PaperrollTopBar
import com.example.paperroll_123.ui.screens.main.MainViewModel

/**
 * Main provider screen containing the WebView, top bar, bottom bar,
 * loading indicators and error handling.
 */
@Composable
fun ProviderScreen(
    trustedUrl: String,
    viewModel: MainViewModel
)
 {

    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var webViewKey by remember { mutableStateOf(0) }

    var rawProgress by remember { mutableStateOf(0f) }
    var isLoading by remember { mutableStateOf(true) }

    val animatedProgress by animateFloatAsState(
        targetValue = rawProgress,
        animationSpec = tween(300),
        label = "progressAnim"
    )

    val error: ErrorType? = viewModel.errorState

    Scaffold(
        topBar = {
            Column {
                PaperrollTopBar()

                AnimatedVisibility(
                    visible = isLoading,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    LinearProgressIndicator(
                        progress = animatedProgress,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        bottomBar = {
            BottomBar(
                webView = webViewRef,
                homeUrl = trustedUrl
            )
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            if (isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }

            key(webViewKey) {
                PaperrollProviderWebView(
                    url = trustedUrl,
                    modifier = Modifier.fillMaxSize(),
                    onWebViewCreated = { webViewRef = it },
                    onLoadingStateChange = { isLoading = it },
                    onProgressChange = { newProgress ->
                        isLoading = newProgress < 100
                        rawProgress = when {
                            newProgress < 90 -> newProgress / 100f
                            newProgress < 100 -> 0.9f
                            else -> 1f
                        }
                    },
                    onError = { type ->
                        viewModel.onWebError(type)
                    }
                )
            }
        }

        if (error != null) {
            ErrorModal(
                error = error,
                onRetry = {
                    viewModel.clearError()
                    webViewKey++
                },
                onHome = {
                    viewModel.clearError()
                    webViewKey++
                },
                onDismiss = {
                    viewModel.clearError()
                }
            )
        }
    }
}
