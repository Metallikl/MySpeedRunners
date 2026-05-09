package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dluche.myspeedrunners.R
import com.dluche.myspeedrunners.extension.RunWithNotNullNorEmpty
import com.dluche.myspeedrunners.extension.applyIf
import com.dluche.myspeedrunners.ui.fake.categoryFakeList
import com.dluche.myspeedrunners.ui.theme.MySpeedRunnersTheme

@Composable
fun ReusableSelectedFlowRowContainer(
    label: String,
    data: List<ReusableSelectedFlowRowData>,
    modifier: Modifier = Modifier,
    onItemClick: ((String) -> Unit)? = null
) {
    var selectedItem by remember { mutableStateOf("") }
    data.RunWithNotNullNorEmpty { items ->
        Column(
            modifier = modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = Bold
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items.forEach { item ->
                    OutlinedCard {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .applyIf(block = {
                                    item.id == selectedItem
                                }) {
                                    background(CardDefaults.cardColors().containerColor)
                                }
                                .padding(8.dp)
                                .clickable(
                                    enabled = onItemClick != null,
                                    onClick = {
                                        selectedItem = item.id
                                        onItemClick?.invoke(item.id)
                                    }
                                ),
                        )
                    }
                }
            }
        }
    }
}

data class ReusableSelectedFlowRowData(
    val id: String,
    val label: String
)

/**
 * Extension function to map any collection to [ReusableSelectedFlowRowData].
 * It receives a lambda that provides the id and label for each item.
 */
fun <T> Collection<T>.toReusableSelectedFlowRowData(
    block: T.((id: String, label: String) -> ReusableSelectedFlowRowData) -> ReusableSelectedFlowRowData
): List<ReusableSelectedFlowRowData> {
    return this.map { item ->
        item.block { id, label ->
            ReusableSelectedFlowRowData(id, label)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryContainerComponentPreview() {
    MySpeedRunnersTheme {
        ReusableSelectedFlowRowContainer(
            label = stringResource(R.string.category_label),
            data = categoryFakeList.toReusableSelectedFlowRowData { mapper ->
                mapper(id, name)
            }
        )
    }
}
