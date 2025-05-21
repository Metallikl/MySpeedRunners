package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.SocialNetwork
import com.dluche.myspeedrunners.domain.model.runner.SocialNetworkType
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.dluche.myspeedrunners.ui.theme.speedrunColor
import com.dluche.myspeedrunners.ui.theme.twitchColor
import com.dluche.myspeedrunners.ui.theme.youtubeColor

@Composable

fun SocialNetworkItem(
    socialNetwork: SocialNetwork,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {}
) {
    Column(
        modifier = modifier
            .size(48.dp)
            .clickable {
                onClick(socialNetwork.url)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val (iconRes, color) = when (socialNetwork.name) {
            SocialNetworkType.TWITTER -> Pair(
                R.drawable.ic_twitter,
                MaterialTheme.colorScheme.onBackground
            )

            SocialNetworkType.YOUTUBE -> Pair(R.drawable.ic_youtube, youtubeColor)
            SocialNetworkType.TWITCH -> Pair(R.drawable.ic_twitch, twitchColor)
            SocialNetworkType.SPEEDRUN_COM -> Pair(R.drawable.ic_trophy_solid, speedrunColor)
            else -> Pair(R.drawable.ic_broadcast, MaterialTheme.colorScheme.primary)
        }

        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = socialNetwork.name.name,
            modifier = Modifier
                .fillMaxSize(),
            tint = color
        )
    }
}

@Preview
@Composable
private fun SocialNetworkItemPreview() {
    MySpeedRunnersTheme {
        SocialNetworkItem(
            socialNetwork = SocialNetwork(
                name = SocialNetworkType.TWITTER,
                url = "https://twitter.com/dluche"
            )
        )
    }
}