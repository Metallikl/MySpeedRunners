package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.domain.model.platform.Platform
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty
import com.dluche.myspeedrunners.ui.fake.platformFakeList
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun PlatformContainerComponent(
    label: String = stringResource(R.string.platforms_label),
    platforms: List<Platform>,
    modifier: Modifier = Modifier
) {
    platforms.RunWithNotNullNorEmpty { platforms ->
        Column(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp)
        ) {
//            Text(
//                text = label,
//                style = MaterialTheme.typography.titleMedium,
//                modifier = Modifier
//                    .fillMaxWidth(),
//                textAlign = TextAlign.Start,
//                color = MaterialTheme.colorScheme.onSurfaceVariant,
//                fontWeight = Bold
//            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                platforms.forEach { platform ->
                    OutlinedCard {
                        Text(
                            text = platform.name,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .padding(8.dp),
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PlatformContainerComponentPreview() {
    MySpeedRunnersTheme {
        PlatformContainerComponent(
            platforms = platformFakeList
        )
    }
}