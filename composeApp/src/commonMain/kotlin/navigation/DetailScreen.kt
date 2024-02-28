package navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.lifecycle.LifecycleEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import core.BackHandler
import data.model.news.ArticleApiModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ui.detail.DetailScreen
import ui.detail.DetailViewModel

class DetailScreen(
    private val articleApiModel: ArticleApiModel
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getScreenModel<DetailViewModel>()

        val detailUiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }

        LifecycleEffect(
            onStarted = {
                scope.launch {
                    viewModel.errorEvent.collectLatest { eventMessage ->
                        snackbarHostState.showSnackbar(message = eventMessage)
                    }
                }
            }
        )

        Scaffold(snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    backgroundColor = Color.Red,
                    contentColor = Color.White,
                ) {
                    Text(text = data.message)
                }
            }
        }) {
            DetailScreen(
                model = articleApiModel,
                detailUiState = detailUiState,
                generateContentGemini = {
                    viewModel.generateContentGemini(articleApiModel.content.orEmpty())
                },
                onNavigateUp = {
                    navigator.pop()
                }
            )
        }

        BackHandler(isEnabled = true) {
            navigator.pop()
        }
    }
}
