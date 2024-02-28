package ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.ui.IconText
import data.model.news.ArticleApiModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import util.Constants.DEFAULT_IMAGE
import util.extension.formatDate

@Composable
fun NewsByCategoryItem(
    model: ArticleApiModel,
    onClickNews: (ArticleApiModel) -> Unit
) {
    Row(
        modifier = Modifier.padding(16.dp).clickable {
            onClickNews(model)
        },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KamelImage(
            resource = asyncPainterResource(
                Url(model.urlToImage ?: DEFAULT_IMAGE)
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(16.dp))
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = model.title ?: "News Title",
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconText(
                    icon = Icons.Default.DateRange,
                    text = model.publishedAt.orEmpty().formatDate()
                )

                IconText(
                    icon = Icons.Default.Info,
                    text = "5 min read"
                )
            }
        }
    }
}
