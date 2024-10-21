package com.example.practivemvvm

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity

class WebViewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        webView.webViewClient = WebViewClient() // To open links within the WebView
        webView.settings.javaScriptEnabled = true

        val url = intent.getStringExtra("URL") ?: ""
        webView.loadUrl(url)

        setContentView(webView)
    }
}