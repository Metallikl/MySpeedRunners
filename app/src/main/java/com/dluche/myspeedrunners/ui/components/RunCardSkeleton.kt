package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme
import com.valentinilk.shimmer.shimmer

@Composable
fun RunCardSkeleton(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .shimmer()
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.LightGray)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(4.dp)
                ,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(154.dp)
                        .height(20.dp)
                        .background(Color.LightGray),
                )
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(20.dp)
                        .background(Color.LightGray),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(20.dp)
                            .background(Color.LightGray),
                    )
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(20.dp)
                            .background(Color.LightGray),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun RunCardPreview() {
    MySpeedRunnersTheme {
        RunCardSkeleton()
    }
}