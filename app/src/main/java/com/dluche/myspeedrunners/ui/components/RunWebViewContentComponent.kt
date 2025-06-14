package com.dluche.myspeedrunners.ui.components

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dluche.myspeedrunners.extension.openBrowser
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable

fun RunWebViewContent(
    url: String,
    modifier: Modifier = Modifier,
    showUrlText: Boolean = false
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()
                settings.loadsImagesAutomatically = true
                settings.javaScriptEnabled = true
                settings.allowFileAccess = true
                settings.javaScriptCanOpenWindowsAutomatically = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.domStorageEnabled = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE
                loadUrl(url)
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
    if(showUrlText) {
        val context = LocalContext.current
        Text(
            text = url,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clickable { url.openBrowser(context) }
        )
    }
}

@Preview
@Composable
private fun RunWebViewContentPreview() {
    MySpeedRunnersTheme {
        RunWebViewContent(
            url = "https://www.google.com"
        )
    }
}