package com.dluche.myspeedrunners.ui.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.SocialNetwork
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import androidx.core.net.toUri
import com.dluche.myspeedrunners.extension.asTwitchEmbeddedUrl
import com.dluche.myspeedrunners.extension.extractTwitchVideoId

@Composable
fun TwitchVideoComponent(
    videoUrl: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val embeddedUrl = videoUrl.asTwitchEmbeddedUrl()
    val videoId = videoUrl.extractTwitchVideoId()

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                openTwitchStream(
                    context,
                    embeddedUrl,
                    videoId
                )
            }
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        SocialNetworkItem(
            socialNetwork = SocialNetwork(SocialNetworkType.TWITCH, videoUrl),
        )

        Text(
            text = stringResource(R.string.twitch_disclaimer_msg),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

private fun openTwitchStream(
    context: Context,
    videoUrl: String,
    videoId: String
) {
    try {
        // Tenta abrir diretamente no app da Twitch
        val intent = Intent(Intent.ACTION_VIEW)
        //intent.data = "twitch://stream/$videoUrl".toUri()//abre stream de um canal
        //intent.data = "twitch://video/$videoId".toUri()//abre stream de um canal
        intent.data = "twitch://open?video= $videoId".toUri()//abre stream de um canal
        intent.setPackage("tv.twitch.android.app")
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        // Se o app não estiver instalado, abre no navegador
        linkToWebpage(context, videoUrl)
    }
}

@Preview
@Composable
private fun TwitchVideoComponentPreview() {
    MySpeedRunnersTheme {
        TwitchVideoComponent(
            ""
        )
    }
}