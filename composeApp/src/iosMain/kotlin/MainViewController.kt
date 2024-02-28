import androidx.compose.ui.window.ComposeUIViewController
import di.networkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.compose.KoinApplication
import util.Action
import util.createStore

fun MainViewController() = ComposeUIViewController {
    KoinApplication( application = {
        modules(networkModule)
    }) {
        App()
    }
}

fun onBackGesture() {
    store.send(Action.OnBackPressed)
}

val store = CoroutineScope(SupervisorJob()).createStore()
