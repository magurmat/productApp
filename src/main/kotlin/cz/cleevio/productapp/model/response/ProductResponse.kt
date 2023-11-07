package cz.cleevio.productapp.model.response

import java.time.Instant

data class ProductResponse(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val createdDate: Instant
)
