package org.waqas028.ktor_kmp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import io.ktor.client.HttpClient
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.waqas028.ktor_kmp.presentation.home.HomeScreen
import org.waqas028.ktor_kmp.presentation.home.HomeVM
import org.waqas028.ktor_kmp.presentation.onboarding.SplashScreen

@Composable
@Preview
fun App(
    navController: NavHostController,
    client: HttpClient,
    modifier: Modifier = Modifier,
) {
    val homeVM: HomeVM = koinViewModel()

    MaterialTheme {
        KoinContext {
            NavHost(
                modifier = modifier,
                navController = navController,
                startDestination = Routes.SplashScreen.route
            ) {

                composable(
                    route = Routes.SplashScreen.route,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    SplashScreen(navController)
                }

                composable(
                    route = Routes.HomeScreen.route,
                    enterTransition = { EnterTransition.None },
                    exitTransition = { ExitTransition.None }
                ) {
                    HomeScreen(homeVM, client)
                }
            }
        }
    }
}