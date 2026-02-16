package com.example.paperroll_123.ui

import android.content.Intent
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Handler
import android.os.Looper
import android.webkit.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import com.example.paperroll_123.domain.errors.ErrorType

@Composable
fun PaperrollProviderWebView(
    url: String,
    modifier: Modifier = Modifier,
    onWebViewCreated: (WebView) -> Unit = {},
    onLoadingStateChange: (Boolean) -> Unit = {},
    onProgressChange: (Int) -> Unit = {},
    onError: (ErrorType) -> Unit
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }

    // ⭐ Nuevo: WebView listo para recibir errores
    var webViewReady by remember { mutableStateOf(false) }

    // ⭐ Timeout manual (60 segundos)
    val timeoutHandler = remember { Handler(Looper.getMainLooper()) }
    var timeoutTriggered by remember { mutableStateOf(false) }

    fun startTimeout(webView: WebView?) {
        timeoutTriggered = false
        timeoutHandler.removeCallbacksAndMessages(null)

        timeoutHandler.postDelayed({
            if (webViewReady && isLoading && !timeoutTriggered) {
                timeoutTriggered = true
                webView?.stopLoading()
                webView?.loadUrl("about:blank")
                onError(ErrorType.Timeout)
            }
        }, 60000)
    }

    Box(modifier = modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                WebView(ctx).apply {

                    // ⭐ WebView creado correctamente
                    onWebViewCreated(this)
                    webViewReady = true

                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    settings.useWideViewPort = true
                    settings.loadWithOverviewMode = true
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW


                    webViewClient = object : WebViewClient() {

                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            isLoading = true
                            onLoadingStateChange(true)


                        }

                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                            onLoadingStateChange(false)
                            timeoutHandler.removeCallbacksAndMessages(null)
                        }

                        override fun onReceivedSslError(
                            view: WebView?,
                            handler: SslErrorHandler?,
                            error: SslError?
                        ) {
                            if (!webViewReady) return

                            view?.stopLoading()
                            view?.loadUrl("about:blank")
                            onError(ErrorType.SslError)
                            handler?.cancel()
                        }

                        override fun onReceivedError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            error: WebResourceError?
                        ) {
                            if (!webViewReady) return
                            if (request?.isForMainFrame != true) return

                            view?.stopLoading()
                            view?.loadUrl("about:blank")

                            val desc = error?.description?.toString()?.lowercase().orEmpty()

                            val type = when {
                                "timeout" in desc -> ErrorType.Timeout
                                "host" in desc -> ErrorType.NoInternet
                                "internet" in desc -> ErrorType.NoInternet
                                "network" in desc -> ErrorType.NoInternet
                                "connection" in desc -> ErrorType.NoInternet
                                "reset" in desc -> ErrorType.NoInternet
                                "unreachable" in desc -> ErrorType.NoInternet
                                "ssl" in desc -> ErrorType.SslError
                                else -> ErrorType.General
                            }

                            onError(type)
                        }

                        override fun onReceivedHttpError(
                            view: WebView?,
                            request: WebResourceRequest?,
                            errorResponse: WebResourceResponse?
                        ) {
                            if (!webViewReady) return
                            if (request?.isForMainFrame != true) return

                            view?.stopLoading()
                            view?.loadUrl("about:blank")
                            onError(ErrorType.General)
                        }

                        // ⭐ Versión correcta: solo interceptamos enlaces especiales
                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            url: String?
                        ): Boolean {
                            if (url == null) return false

                            return when {
                                url.startsWith("link:") -> {
                                    context.startActivity(
                                        Intent(Intent.ACTION_VIEW, url.toUri())
                                    )
                                    true
                                }

                                else -> false // ⭐ Permitir que el WebView cargue TODO lo demás
                            }
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            val newUrl = request?.url?.toString() ?: return false
                            return shouldOverrideUrlLoading(view, newUrl)
                        }
                    }

                    webChromeClient = object : WebChromeClient() {
                        override fun onProgressChanged(view: WebView?, newProgress: Int) {
                            if (webViewReady) {
                                onProgressChange(newProgress)
                            }
                        }
                    }

                    loadUrl(url)
                }
            }
        )

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
