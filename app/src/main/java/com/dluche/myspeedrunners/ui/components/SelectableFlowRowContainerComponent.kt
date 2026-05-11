package com.dluche.myspeedrunners.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
fun SelectableFlowRowContainer(
    label: String,
    data: List<SelectableFlowRowData>,
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
                    OutlinedCard(
                        modifier = Modifier
                            .clickable(
                                enabled = onItemClick != null,
                                onClick = {
                                    selectedItem = item.id
                                    onItemClick?.invoke(item.id)
                                }
                            )
                    ) {
                        Text(
                            text = item.label,
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier
                                .applyIf({ item.id == selectedItem }) {
                                    background(CardDefaults.cardColors().containerColor)
                                }
                                .padding(8.dp),
                        )
                    }
                }
            }
        }
    }
}

data class SelectableFlowRowData(
    val id: String,
    val label: String
)

/**
 * Extension function to map any collection to [SelectableFlowRowData].
 * It receives a lambda that provides the id and label for each item.
 */
fun <T> Collection<T>.mapToSelectableFlowRowData(
    block: T.((id: String, label: String) -> SelectableFlowRowData) -> SelectableFlowRowData
): List<SelectableFlowRowData> {
    return this.map { item ->
        item.block { id, label ->
            SelectableFlowRowData(id, label)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelectableFlowRowContainerPreview() {
    MySpeedRunnersTheme {
        SelectableFlowRowContainer(
            label = stringResource(R.string.category_label),
            data = categoryFakeList.mapToSelectableFlowRowData { mapper ->
                mapper(id, name)
            }
        )
    }
}
