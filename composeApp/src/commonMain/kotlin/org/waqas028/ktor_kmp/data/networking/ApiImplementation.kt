package org.waqas028.ktor_kmp.data.networking

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.waqas028.ktor_kmp.data.dto.ProductDTO
import org.waqas028.ktor_kmp.data.response.Product
import org.waqas028.ktor_kmp.data.util.NetworkError
import org.waqas028.ktor_kmp.data.util.Result

class ApiImplementation : APIInterface {
    override suspend fun getProductList(productDTO: ProductDTO): Result<List<Product>, NetworkError> {
        val response = try {
            productDTO.httpClient.get(
                urlString = "products/"
            ) {
                parameter("offset", productDTO.offset)
                parameter("limit", productDTO.limit)
            }
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }

        return when(response.status.value) {
            in 200..299 -> {
                val productResponse = response.body<List<Product>>()
                Result.Success(productResponse)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}