package org.waqas028.ktor_kmp

import androidx.compose.ui.window.ComposeUIViewController
import androidx.navigation.compose.rememberNavController
import io.ktor.client.engine.darwin.Darwin
import org.waqas028.ktor_kmp.data.di.initKoin
import org.waqas028.ktor_kmp.data.networking.createHttpClient
import org.waqas028.ktor_kmp.presentation.navigation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    val navController = rememberNavController()
    App(navController, createHttpClient(Darwin.create()))
}