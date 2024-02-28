package ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.model.news.ArticleApiModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import util.extension.formatDate

fun LazyListScope.breakingNewsItem(
    breakingNews: List<ArticleApiModel>,
    onClickNews: (ArticleApiModel) -> Unit
) {
    item {
        LazyRow {
            items(breakingNews) { model ->
                BreakingNews(model = model, onClickNews = onClickNews)
            }
        }
    }
}

@Composable
fun BreakingNews(
    model: ArticleApiModel,
    onClickNews: (ArticleApiModel) -> Unit
) {
    Box(
        Modifier
            .padding(16.dp)
            .width(300.dp)
            .fillMaxHeight(.4f)
            .shadow(1.dp, RoundedCornerShape(16.dp))
            .clickable { onClickNews(model) }
    ) {
        KamelImage(
            resource = asyncPainterResource(Url(model.urlToImage.orEmpty())),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(400.dp).clip(RoundedCornerShape(16.dp))
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                model.title.orEmpty(),
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 3,
                minLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = model.author.orEmpty(),
                    color = Color.Gray,
                    fontSize = 13.sp,
                )
                Text(model.publishedAt.orEmpty().formatDate(), color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}
