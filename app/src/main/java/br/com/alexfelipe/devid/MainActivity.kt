package br.com.alexfelipe.devid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import br.com.alexfelipe.devid.ui.screens.MainScreen
import br.com.alexfelipe.devid.ui.theme.DevIdTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DevIdTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        "https://avatars.githubusercontent.com/u/8989346?v=4",
                        "Alex Felipe",
                        "I'm a software developer, so let's start the code",
                        listOf("Android", "Kotlin", "Java", "Spring framework")
                    )
                }
            }
        }
    }
}
