package ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.model.news.ArticleApiModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import util.Constants.DEFAULT_IMAGE
import util.extension.formatDate
import util.extension.or

@OptIn(ExperimentalResourceApi::class)
@Composable
fun DetailScreen(
    model: ArticleApiModel,
    detailUiState: DetailUiState,
    generateContentGemini: () -> Unit,
    onNavigateUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        AnimatedVisibility(detailUiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Black
            )
        }

        Box {
            KamelImage(
                resource = asyncPainterResource(Url(model.urlToImage or DEFAULT_IMAGE)),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
                onLoading = { progress ->
                    CircularProgressIndicator(
                        progress = progress,
                    )
                }
            )

            IconButton(
                modifier = Modifier.padding(8.dp).align(Alignment.TopStart),
                onClick = {
                    onNavigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = Color.White
                )
            }
        }

        Text(
            text = model.title or "Türkiye Cennet",
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier.size(36.dp),
                    painter = painterResource("compose-multiplatform.xml"),
                    contentDescription = ""
                )

                Text(text = "Hi, Compose", fontWeight = FontWeight.Bold)
            }

            Text(text = model.publishedAt?.formatDate() or "22 Feb, 2024", color = Color.Gray)
        }

        Text(
            text = model.description or "Türkiye cennet cennet",
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
        )

        Text(
            text = detailUiState.generatedText.ifEmpty { model.content or "Türkiye cennet cennet" },
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(16.dp)
        )

        Button(
            modifier = Modifier.wrapContentWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Color.Black
            ),
            onClick = {
                generateContentGemini()
            }
        ) {
            Text(text = "Generate with AI")
            Image(painterResource(res = "gemini_icon.xml"), contentDescription = "")
        }

        Spacer(modifier = Modifier.height(64.dp))
    }
}
