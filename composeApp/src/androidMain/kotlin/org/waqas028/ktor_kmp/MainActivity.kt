package org.waqas028.ktor_kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import io.ktor.client.engine.okhttp.OkHttp
import org.waqas028.ktor_kmp.data.networking.createHttpClient
import org.waqas028.ktor_kmp.presentation.navigation.App
import org.waqas028.ktor_kmp.utils.ContextUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ContextUtils.setContext(context = this)
        setContent {
            val navController = rememberNavController()
            App(navController, createHttpClient(OkHttp.create()))
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    val navController = rememberNavController()
    App(navController, createHttpClient(OkHttp.create()))
}