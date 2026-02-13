package com.example.paperroll_123.ui.screens.disclaimer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Disclaimer screen shown before accessing the provider view.
 * The user must explicitly accept the terms before continuing.
 */
@Composable
fun DisclaimerScreen(
    onAccepted: () -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Before continuing",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Paperroll‑123 informs that all transactions, purchases, sales, " +
                    "shipping processes and invoicing will be handled by an authorized " +
                    "commercial partner. By continuing, you acknowledge and accept that " +
                    "all operational and commercial responsibility lies exclusively with " +
                    "this partner.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = checked,
                onCheckedChange = { checked = it }
            )
            Text("I accept the terms")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onAccepted,
            enabled = checked,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue")
        }
    }
}
