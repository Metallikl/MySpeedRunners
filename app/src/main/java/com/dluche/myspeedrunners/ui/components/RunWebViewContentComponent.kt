package com.dluche.myspeedrunners.ui.components

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.SocialNetwork
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType
import com.dluche.myspeedrunners.extension.openBrowser
import com.dluche.myspeedrunners.ui.feature.gamedetails.screen.ModeratorsContainer
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun RunWebViewContent(
    url: String,
    modifier: Modifier = Modifier,
    showUrlText: Boolean = false
) {
    val context = LocalContext.current
    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    webViewClient = WebViewClient()
                    webChromeClient = WebChromeClient().apply {
                        setLayerType(View.LAYER_TYPE_HARDWARE, null)
                    }
                    settings.loadsImagesAutomatically = true
                    settings.javaScriptEnabled = true
                    settings.allowFileAccess = true
                    settings.javaScriptCanOpenWindowsAutomatically = true
                    settings.mediaPlaybackRequiresUserGesture = false
                    settings.domStorageEnabled = true
                    settings.cacheMode = WebSettings.LOAD_NO_CACHE
                    setLayerType(View.LAYER_TYPE_HARDWARE, null)
                    loadUrl(url)
                }
            },
            update = { webView ->
                webView.loadUrl(url)
            }
        )
        //todo criar outra componetn pra twitch , sem exibir o video, apenas esse box e um disclaimer que twitch nã permite exibir
        if(url.contains(SocialNetworkType.TWITCH.name.lowercase())) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                SocialNetworkItem(
                    socialNetwork = SocialNetwork(SocialNetworkType.TWITCH, url),
                    onClick = {
                        linkToWebpage(context, it)
                    }
                )
            }
        }
    }

    if(showUrlText) {
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