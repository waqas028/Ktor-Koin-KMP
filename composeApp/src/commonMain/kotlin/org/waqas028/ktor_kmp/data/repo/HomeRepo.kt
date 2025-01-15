package org.waqas028.ktor_kmp.data.repo

import org.waqas028.ktor_kmp.data.dto.ProductDTO
import org.waqas028.ktor_kmp.data.networking.ApiImplementation
import org.waqas028.ktor_kmp.data.response.Product
import org.waqas028.ktor_kmp.data.util.NetworkError
import org.waqas028.ktor_kmp.data.util.Result

class HomeRepo(private val apiImplementation: ApiImplementation) {
    suspend fun getProductList(productDTO: ProductDTO) : Result<List<Product>, NetworkError> {
        return apiImplementation.getProductList(productDTO)
    }
}