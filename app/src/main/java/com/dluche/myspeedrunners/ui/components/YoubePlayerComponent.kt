package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dluche.myspeedrunners.extension.openBrowser
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun YoutubePlayerComponent(
    videoId: String,
    modifier: Modifier = Modifier,
    showUrlText: Boolean = false
) {
    val context = LocalContext.current

    val youTubePlayerView = remember {
        YouTubePlayerView(context).apply {
            enableAutomaticInitialization = false
            initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            })
        }
    }

    AndroidView(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .height(200.dp),
        factory = {
            youTubePlayerView
        },
    )

    if(showUrlText) {
        val url = "https://www.youtube.com/watch?v=$videoId"
        Text(
            text = url,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clickable { url.openBrowser(context) }
        )
    }

    DisposableEffect(Unit) {
        onDispose {
            youTubePlayerView.release()
        }
    }
}

@Preview
@Composable
private fun YouTubePlayerComponentPreview() {
    MySpeedRunnersTheme {
        YoutubePlayerComponent(
            videoId = "fV7TZJCa9c8",
            modifier = Modifier,
            showUrlText = false
        )
    }
}