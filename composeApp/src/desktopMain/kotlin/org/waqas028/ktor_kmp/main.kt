package org.waqas028.ktor_kmp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import io.ktor.client.engine.okhttp.OkHttp
import org.waqas028.ktor_kmp.data.di.initKoin
import org.waqas028.ktor_kmp.data.networking.createHttpClient
import org.waqas028.ktor_kmp.presentation.navigation.App

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "Ktor_KMP",
    ) {
        val navController = rememberNavController()
        App(navController, createHttpClient(OkHttp.create()))
    }
}