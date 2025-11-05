package com.dluche.myspeedrunners.ui.components

import android.text.TextUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.DriveFileMove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.domain.model.runner.RoleEnum
import com.dluche.myspeedrunners.domain.model.runner.RunnerCard
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.dluche.myspeedrunners.ui.utils.getRunnerGradientColor
import com.valentinilk.shimmer.shimmer

@ExperimentalMaterial3Api
@Composable
fun RunnerRunTopBar(
    runnerCard: RunnerCard,
    modifier: Modifier = Modifier,
    onBackClick : () -> Unit = {}
) {
    val backgroundColor = getRunnerGradientColor(nameStyle = runnerCard.nameStyle)

    TopAppBar(
        modifier = modifier,
//        modifier = modifier.background(
//            brush = backgroundColor
//        ),
//        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackClick()
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    ,
                horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
                verticalAlignment = Alignment.CenterVertically

            ) {

                RunnerImage(
                    imageUrl = runnerCard.imageUrl.orEmpty(),
                    imageSize = 48.dp,
                    imageErrorSize = 48.dp
                )

                Text(
                    text = runnerCard.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        brush = backgroundColor
                    ),
                    maxLines = 1,
                )
            }
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun RunnerRunTopBarSkeleton(
    modifier: Modifier = Modifier,
    onBackClick : () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = {
                    onBackClick()
                },
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.Start),
                verticalAlignment = Alignment.CenterVertically

            ) {

                ImageSkeletonPlaceholder(
                    size = 48.dp,
                    clip = CircleShape
                )

                Box(
                    modifier = Modifier
                        .shimmer()
                        .fillMaxWidth(0.6f)
                        .height(20.dp)
                        .background(Color.LightGray),
                )
            }

        }
    )
}



@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
private fun RunnerRunTopBarPreview() {
    MySpeedRunnersTheme {
        RunnerRunTopBar(
            runnerCard = RunnerCard(
                id = "",
                name = "Runner Name",
                imageUrl = null,
                nameStyle = null,
                role = RoleEnum.MODERATOR,
                pronouns = null,
                japaneseName = null,
                locationUrl = null,
                location = null,
                signup = null
            )
        )
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
private fun RunnerRunTopBarSkeletonPreview() {
    MySpeedRunnersTheme {
        RunnerRunTopBarSkeleton()
    }
}