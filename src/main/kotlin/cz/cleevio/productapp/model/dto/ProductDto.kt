package cz.cleevio.productapp.model.dto

import java.time.Instant

data class ProductDto(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val createdDate: Instant
)
