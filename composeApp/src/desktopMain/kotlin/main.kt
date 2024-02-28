import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.networkModule
import navigation.HomeScreen
import org.koin.compose.KoinApplication
import java.awt.Dimension

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        window.minimumSize = Dimension(720, 720)
        KoinApplication(application = {
            modules(networkModule)
        }) {
            Navigator(HomeScreen) {
                SlideTransition(it)
            }
        }
    }
}
