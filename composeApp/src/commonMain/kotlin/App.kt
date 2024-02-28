import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import navigation.HomeScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(HomeScreen) { navigator ->
            SlideTransition(navigator)
        }
    }
}
