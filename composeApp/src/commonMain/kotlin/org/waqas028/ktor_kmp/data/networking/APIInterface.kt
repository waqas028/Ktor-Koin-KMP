package org.waqas028.ktor_kmp.data.networking

import org.waqas028.ktor_kmp.data.dto.ProductDTO
import org.waqas028.ktor_kmp.data.response.Product
import org.waqas028.ktor_kmp.data.util.NetworkError
import org.waqas028.ktor_kmp.data.util.Result

interface APIInterface {
    suspend fun getProductList(productDTO: ProductDTO): Result<List<Product>, NetworkError>
}