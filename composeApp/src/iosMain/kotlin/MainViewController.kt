import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.lifecycle.ApplicationLifecycle
import platform.UIKit.UIViewController
import dev.mateuy.decomposenavigationsample.ui.RootComponent
import dev.mateuy.decomposenavigationsample.ui.RootView

fun MainViewController(): UIViewController {
    val root = RootComponent(
        componentContext= DefaultComponentContext(lifecycle= ApplicationLifecycle())
    )
    return ComposeUIViewController { RootView(root) }
}
