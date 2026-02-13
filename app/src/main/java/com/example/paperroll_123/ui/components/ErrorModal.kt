package com.example.paperroll_123.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.paperroll_123.domain.errors.ErrorType

@Composable
fun ErrorModal(
    error: ErrorType,
    onRetry: () -> Unit,
    onHome: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,

        // ⭐ Modal title
        title = {
            Text(
                text = "Error",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },

        // ⭐ Modal message
        text = {
            Text(
                text = error.message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },

        // ⭐ Primary button → Retry
        confirmButton = {
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    "Retry",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        },

        // ⭐ Secondary buttons → Home + Close
        dismissButton = {
            Row {
                TextButton(onClick = onHome) {
                    Text(
                        "Home",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                TextButton(onClick = onDismiss) {
                    Text(
                        "Close",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },

        // ⭐ Modal background
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    )
}
