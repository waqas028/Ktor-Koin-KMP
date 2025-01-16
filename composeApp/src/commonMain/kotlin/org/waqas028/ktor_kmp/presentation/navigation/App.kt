package org.waqas028.ktor_kmp.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.ktor.client.HttpClient
import okio.FileSystem
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.waqas028.ktor_kmp.presentation.home.HomeScreen
import org.waqas028.ktor_kmp.presentation.home.HomeVM
import org.waqas028.ktor_kmp.presentation.onboarding.SplashScreen

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App(
    navController: NavHostController,
    client: HttpClient,
    modifier: Modifier = Modifier,
) {
    val homeVM: HomeVM = koinViewModel()

    MaterialTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }

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

fun getAsyncImageLoader(context: PlatformContext) =
    ImageLoader.Builder(context).memoryCachePolicy(CachePolicy.ENABLED).memoryCache {
        MemoryCache.Builder().maxSizePercent(context, 0.3).strongReferencesEnabled(true).build()
    }.diskCachePolicy(CachePolicy.ENABLED).networkCachePolicy(CachePolicy.ENABLED).diskCache {
        newDiskCache()
    }.crossfade(true).logger(DebugLogger()).build()

fun newDiskCache(): DiskCache {
    return DiskCache.Builder().directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
        .maxSizeBytes(1024L * 1024 * 1024) // 512MB
        .build()
}