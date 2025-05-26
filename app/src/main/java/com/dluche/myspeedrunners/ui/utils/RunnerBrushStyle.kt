package com.dluche.myspeedrunners.ui.utils

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import com.dluche.myspeedrunners.domain.model.runner.NameStyle
import com.dluche.myspeedrunners.domain.model.runner.NameStyleEnum

@Composable
fun getRunnerGradientColor(isDarkTheme: Boolean = isSystemInDarkTheme(), nameStyle: NameStyle?): Brush {
    val brushList = mutableListOf<Color>()
    nameStyle?.let { nameStyle ->
        when (nameStyle.style) {
            NameStyleEnum.GRADIENT -> {
                if (isDarkTheme) {
                    if (nameStyle.colorFrom?.dark != null && nameStyle.colorTo?.dark != null) {
                        brushList.add(Color(nameStyle.colorFrom.dark.toColorInt()))
                        brushList.add(Color(nameStyle.colorTo.dark.toColorInt()))
                    } else {
                        brushList.add(MaterialTheme.colorScheme.onBackground)
                    }
                } else {
                    if (nameStyle.colorFrom?.light != null && nameStyle.colorTo?.light != null) {
                        brushList.add(Color(nameStyle.colorFrom.light.toColorInt()))
                        brushList.add(Color(nameStyle.colorTo.light.toColorInt()))
                    } else {
                        brushList.add(MaterialTheme.colorScheme.onBackground)
                    }
                }
            }

            NameStyleEnum.SOLID -> {
                if (isDarkTheme) {
                    nameStyle.color?.dark?.let {
                        brushList.add(Color(it.toColorInt()))
                    }
                } else {
                    nameStyle.color?.light?.let {
                        brushList.add(Color(it.toColorInt()))
                    }
                }
            }

            else -> brushList.add(MaterialTheme.colorScheme.onBackground)
        }
    }
    if (brushList.isEmpty()) {
        brushList.add(MaterialTheme.colorScheme.onBackground)
    }
    if (brushList.size == 1) {
        brushList.add(
            brushList.first().copy(alpha = 0.7f)
        )
    }

    return  Brush.horizontalGradient(brushList)
}