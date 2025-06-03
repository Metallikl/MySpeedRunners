package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material.icons.outlined.NotInterested
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Icon
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
    var background by remember { mutableStateOf(MySpeedRunColors.warningContainer) }
    var icon: ImageVector by remember { mutableStateOf(Icons.Filled.Star) }

    when (runStatus) {
        RunStatusEnum.NEW -> {
            color = MySpeedRunColors.warning
            background = MySpeedRunColors.warningContainer
            icon = Icons.Outlined.Stars
        }

        RunStatusEnum.VERIFIED -> {
            color = MySpeedRunColors.success
            background = MySpeedRunColors.successContainer
            icon = Icons.Outlined.CheckCircle
        }
        RunStatusEnum.REJECTED -> {
            color = MySpeedRunColors.error
            background = MySpeedRunColors.errorContainer
            icon = Icons.Outlined.ErrorOutline
        }
        RunStatusEnum.UNKNOWN -> {
            color = Color.Gray
            background = Color.LightGray
            icon = Icons.Outlined.NotInterested
        }
    }

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .border(1.dp, color, CircleShape)
            .background(background.copy(alpha = 0.9f), CircleShape)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally)
    ){

        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = color,
            modifier = Modifier.padding(end = 4.dp)
        )

        Text(
           text = text,
            color = color,
        )

    }


}

@Preview
@Composable
private fun RunStatusComponentPreview(
    @PreviewParameter(RunStatusPreviewParam::class)
    runStaTus : RunStatusEnum
) {
    MySpeedRunnersTheme {
        RunStatusComponent(
            runStaTus
        )
    }
}