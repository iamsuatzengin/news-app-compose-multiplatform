package ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.news.ArticleApiModel
import ui.home.component.NewsByCategoryItem
import ui.home.component.breakingNewsItem
import ui.home.component.homeHeader
import ui.home.component.newsCategoriesTab

@Composable
fun HomeScreen(
    homeUiState: HomeUiState,
    onSelectCategory: (String) -> Unit,
    onClickNews: (ArticleApiModel) -> Unit
) {
    var selectedCategoryIndex by rememberSaveable { mutableIntStateOf(0) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        homeHeader()

        item {
            Text(
                "Breaking News", fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        breakingNewsItem(
            breakingNews = homeUiState.breakingNews,
            onClickNews = onClickNews
        )

        newsCategoriesTab(
            selectedCategoryIndex,
            onSelectIndex = { index, category ->
                selectedCategoryIndex = index
                onSelectCategory(category)
            }
        )

        items(items = homeUiState.newsByCategory) { model ->
            NewsByCategoryItem(model = model, onClickNews = onClickNews)
        }
    }
}
