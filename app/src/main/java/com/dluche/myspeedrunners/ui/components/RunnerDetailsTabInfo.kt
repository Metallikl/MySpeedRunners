package com.dluche.myspeedrunners.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.SocialNetwork
import com.dluche.myspeedrunners.extension.getTranslation
import com.dluche.myspeedrunners.ui.fake.runner1
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun InfoContent(
    runner: Runner,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        runner.location?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                AsyncImage(
                    model = runner.locationUrl,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    placeholder = painterResource(id = R.drawable.ic_map_marker_radius),
                    error = painterResource(id = R.drawable.ic_map_marker_radius),
                    colorFilter = if (runner.locationUrl == null) {
                        ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                    } else null
                )

                Text(
                    text = runner.location,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        runner.pronouns?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    Icons.Filled.PersonPin,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                Icons.Filled.Work,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.onSurface
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = runner.role.getTranslation(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        runner.signup?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Launch,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        runner.socialNetworks?.let {
            SocialNetworkContent(it)
        }
    }
}

@Composable
fun SocialNetworkContent(
    socialNetworks: List<SocialNetwork>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        socialNetworks.forEach {
            SocialNetworkItem(
                socialNetwork = it,
                onClick = {
                    linkToWebpage(context, it)
                }
            )
        }
    }
}

fun linkToWebpage(context: Context, url: String) {
    val openURL = Intent(Intent.ACTION_VIEW)
    openURL.data = url.toUri()
    context.startActivity(openURL)
}

@Preview(showBackground = true)
@Composable
private fun RunnerDetailsTabInfoPreview() {
    MySpeedRunnersTheme {
        InfoContent(
            runner1
        )
    }
}
