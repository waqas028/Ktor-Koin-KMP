@file:Suppress("DEPRECATION")

package org.waqas028.ktor_kmp.data.di

import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.waqas028.ktor_kmp.data.networking.APIInterface
import org.waqas028.ktor_kmp.data.networking.ApiImplementation
import org.waqas028.ktor_kmp.data.repo.GenericPagingRepository
import org.waqas028.ktor_kmp.data.repo.HomeRepo
import org.waqas028.ktor_kmp.presentation.home.HomeVM

val sharedModule = module {
    viewModelOf(::HomeVM)
    singleOf(::HomeRepo)
    singleOf(::GenericPagingRepository)
    singleOf(::ApiImplementation) { bind<APIInterface>() }
}