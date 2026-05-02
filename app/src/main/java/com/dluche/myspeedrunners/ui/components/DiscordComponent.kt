package com.dluche.myspeedrunners.ui.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun DiscordComponent(
    discordUrl: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.Start)
            .clickable(onClick = {openDiscordIntent(context, discordUrl) })
    ) {
        //Fixme wip
        Row(
            modifier = Modifier.padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_discord2),
                contentDescription = stringResource(R.string.discord_label),
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text =  stringResource(R.string.discord_label),
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .wrapContentWidth(),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

fun openDiscordIntent(context: Context, discordUrl: String) {
    try {
        Intent(Intent.ACTION_VIEW, discordUrl.toUri()).apply {
            context.startActivity(this)
        }
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context,
            context.getString(R.string.discord_activity_not_found), Toast.LENGTH_SHORT).show()
    }
}

@Preview
@Composable
private fun DiscordComponentPreview() {
    MySpeedRunnersTheme {
        DiscordComponent(
            discordUrl = "https://discord.com/invite/92xARJJsWg"
        )
    }
}