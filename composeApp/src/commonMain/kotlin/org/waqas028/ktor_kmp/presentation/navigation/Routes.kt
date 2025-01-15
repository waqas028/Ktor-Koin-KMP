package org.waqas028.ktor_kmp.presentation.navigation

sealed class Routes(val route: String){
    data object SplashScreen : Routes("SplashScreen")
    data object HomeScreen : Routes("HomeScreen")
}