package org.waqas028.ktor_kmp.data.dto

import io.ktor.client.HttpClient

data class ProductDTO(
    val httpClient: HttpClient,
    val offset: Int,
    val limit: Int,
)
