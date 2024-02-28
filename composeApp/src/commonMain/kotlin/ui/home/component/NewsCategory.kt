package ui.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.news.CategoryType
import util.extension.uppercaseFirst

fun LazyListScope.newsCategoriesTab(
    selectedCategoryIndex: Int,
    onSelectIndex: (Int, String) -> Unit,
) = item {
    NewsCategory(
        selectedIndex = selectedCategoryIndex,
        onSelectIndex = onSelectIndex
    )
}

@Composable
fun NewsCategory(
    selectedIndex: Int,
    onSelectIndex: (Int, String) -> Unit,
) {
    val categories =  CategoryType.entries.toList()

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(categories) { index: Int, category: CategoryType ->
            NewsCategoryItem(
                category = category.name,
                isSelected = index == selectedIndex,
                onSelect = {
                    onSelectIndex(index, CategoryType.getCategoryQuery(category))
                }
            )
        }
    }
}

@Composable
fun NewsCategoryItem(
    category: String,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var textWidth by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .wrapContentWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    radius = 16.dp,
                    color = Color.Red
                ),
                onClick = {
                    onSelect()

                },
            )
            .padding(horizontal = 16.dp),

        ) {
        Text(
            text = category.uppercaseFirst(),
            fontSize = 16.sp,
            modifier = Modifier.onGloballyPositioned {
                textWidth = it.size.width
            }
        )
        if (isSelected) {
            Canvas(modifier = Modifier.fillMaxWidth()) {
                drawCircle(
                    color = Color.DarkGray,
                    radius = 10f,
                    center = Offset(x = textWidth / 2f, 4.dp.toPx())
                )
            }
        }
    }
}
