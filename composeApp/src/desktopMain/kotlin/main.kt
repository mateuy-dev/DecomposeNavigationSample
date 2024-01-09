import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.mateuy.decomposenavigationsample.App
import dev.mateuy.decomposenavigationsample.UseCases
import dev.mateuy.decomposenavigationsample.ui.RootComponent
import dev.mateuy.decomposenavigationsample.ui.RootView
import javax.swing.SwingUtilities

fun main()  {
    val lifecycle = LifecycleRegistry()
    val root =
        runOnUiThread {
            RootComponent(
                componentContext = DefaultComponentContext(lifecycle = lifecycle),
                UseCases()
            )
        }
    application {
        Window(onCloseRequest = ::exitApplication, title = "DecomposeNavigationSample") {
            RootView(root)
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview() {
    App()
}

internal fun <T> runOnUiThread(block: () -> T): T {
    if (SwingUtilities.isEventDispatchThread()) {
        return block()
    }

    var error: Throwable? = null
    var result: T? = null

    SwingUtilities.invokeAndWait {
        try {
            result = block()
        } catch (e: Throwable) {
            error = e
        }
    }

    error?.also { throw it }

    @Suppress("UNCHECKED_CAST")
    return result as T
}