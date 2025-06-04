package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.outlined.NotInterested
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.domain.model.run.RunStatusEnum
import com.dluche.myspeedrunners.extension.getTranslation
import com.dluche.myspeedrunners.ui.previewparameter.RunStatusPreviewParam
import com.dluche.myspeedrunners.ui.theme.MySpeedRunColors
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme


@Composable
fun RunStatusComponent(
    runStatus: RunStatusEnum,
    modifier: Modifier = Modifier
) {
    val text = runStatus.getTranslation()
    var color by remember { mutableStateOf(MySpeedRunColors.warning) }
    var icon: ImageVector by remember { mutableStateOf(Icons.Filled.Star) }

    when (runStatus) {
        RunStatusEnum.NEW -> {
            color = MySpeedRunColors.newYellow
            icon = Icons.Filled.Stars
        }

        RunStatusEnum.VERIFIED -> {
            color = MySpeedRunColors.verifiedGreen
            icon = Icons.Filled.CheckCircle
        }

        RunStatusEnum.REJECTED -> {
            color = MySpeedRunColors.rejectedRed
            icon = Icons.Filled.Error
        }

        RunStatusEnum.UNKNOWN -> {
            color = Color.Gray
            icon = Icons.Outlined.NotInterested
        }
    }

    Row(
        modifier = modifier
            .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
    ) {

        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = color,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 4.dp)
        )

        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.labelSmall
        )
    }


}

@Preview
@Composable
private fun RunStatusComponentPreview(
    @PreviewParameter(RunStatusPreviewParam::class)
    runStaTus: RunStatusEnum
) {
    MySpeedRunnersTheme {
        RunStatusComponent(
            runStaTus
        )
    }
}