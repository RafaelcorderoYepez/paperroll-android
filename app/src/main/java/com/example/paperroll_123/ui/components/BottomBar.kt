package com.example.paperroll_123.ui.components

import android.content.Intent
import android.webkit.WebView
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.sharp.ExitToApp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.compose.foundation.layout.*

@Composable
fun BottomBar(
    webView: WebView?,
    homeUrl: String
) {
    val context = LocalContext.current

    var barPressed by remember { mutableStateOf(false) }

    val barElevation by animateDpAsState(
        targetValue = if (barPressed) 8.dp else 0.dp,
        animationSpec = tween(180),
        label = "barElevation"
    )

    Surface(
        tonalElevation = barElevation,
        color = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {

            fun pressEffect(action: () -> Unit) {
                barPressed = true
                action()
                barPressed = false
            }

            @Composable
            fun AnimatedIcon(
                image: ImageVector,
                description: String,
                onClick: () -> Unit
            ) {
                var pressed by remember { mutableStateOf(false) }

                val scale by animateFloatAsState(
                    targetValue = if (pressed) 1.25f else 1f,
                    animationSpec = tween(150),
                    label = "iconScale"
                )

                IconButton(
                    onClick = {
                        pressed = true
                        pressEffect { onClick() }
                        pressed = false
                    }
                ) {
                    Icon(
                        imageVector = image,
                        contentDescription = description,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .scale(scale)
                            .size(26.dp)
                    )
                }
            }

            AnimatedIcon(
                image = Icons.AutoMirrored.Filled.ArrowBack,
                description = "Back",
                onClick = { if (webView?.canGoBack() == true) webView.goBack() }
            )

            AnimatedIcon(
                image = Icons.AutoMirrored.Filled.ArrowForward,
                description = "Forward",
                onClick = { if (webView?.canGoForward() == true) webView.goForward() }
            )

            AnimatedIcon(
                image = Icons.Filled.Home,
                description = "Home",
                onClick = { webView?.loadUrl(homeUrl) }
            )

            AnimatedIcon(
                image = Icons.Filled.Refresh,
                description = "Reload",
                onClick = { webView?.reload() }
            )

            AnimatedIcon(
                image = Icons.Sharp.ExitToApp,
                description = "Open in browser",
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, homeUrl.toUri())
                    context.startActivity(intent)
                }
            )
        }
    }
}
