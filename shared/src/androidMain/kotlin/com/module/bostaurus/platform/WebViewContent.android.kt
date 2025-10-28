package com.module.bostaurus.platform

import android.annotation.SuppressLint
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
actual fun WebViewContent(url: String) {
    Surface(
        modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection())
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                    isVerticalScrollBarEnabled = true
                    overScrollMode = View.OVER_SCROLL_ALWAYS
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            }
        )
    }
}