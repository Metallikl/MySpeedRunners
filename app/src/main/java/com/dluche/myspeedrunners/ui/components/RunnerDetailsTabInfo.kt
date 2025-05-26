package com.dluche.myspeedrunners.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Launch
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.runner.Runner
import com.dluche.myspeedrunners.domain.model.runner.SocialNetwork
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty
import com.dluche.myspeedrunners.extension.getTranslation
import com.dluche.myspeedrunners.ui.fake.runner1
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun RunnerDetailsInfo(
    runner: Runner,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        runner.location.RunWithNotNullNorEmpty { locationName ->
            val painter = rememberAsyncImagePainter(runner.locationUrl)
            val locationUrlState = painter.state.collectAsState()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                when(locationUrlState.value){
                    is AsyncImagePainter.State.Success -> {
                        Image(
                            painter = painter,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )
                    }
                    else ->{
                        Image(
                            painter = painterResource(id = R.drawable.ic_map_marker_radius),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                        )
                    }
                }

                Text(
                    text = locationName,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        runner.pronouns.RunWithNotNullNorEmpty{ pronouns ->
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
                    text = pronouns,
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
                Icons.Filled.Badge,
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
        RunnerDetailsInfo(
            runner1
        )
    }
}
