package org.waqas028.ktor_kmp.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ProductsResponse(
    val product: List<Product>
)

@Serializable
data class Product(
    val id: Int,
    val title: String,
    val price: Int,
    val description: String,
    val images: List<String>,
    val creationAt: String,
    val updatedAt: String,
    val category: Category
)

@Serializable
data class  Category(
    val id: Int,
    val name: String,
    val image: String,
    val creationAt: String,
    val updatedAt: String
)