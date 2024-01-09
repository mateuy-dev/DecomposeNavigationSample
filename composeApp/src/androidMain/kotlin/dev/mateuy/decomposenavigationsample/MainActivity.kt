package dev.mateuy.decomposenavigationsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import dev.mateuy.decomposenavigationsample.ui.RootComponent
import dev.mateuy.decomposenavigationsample.ui.RootView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root =
            RootComponent(
                componentContext = defaultComponentContext(),
            )
        setContent {
            RootView(root)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}