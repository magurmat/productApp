package cz.cleevio.productapp.mapper

import cz.cleevio.productapp.domain.Product
import cz.cleevio.productapp.model.command.CreateProductCommand
import cz.cleevio.productapp.model.dto.ProductDto
import cz.cleevio.productapp.model.request.ProductRequest
import cz.cleevio.productapp.model.response.ProductResponse

fun ProductRequest.toCreateProductCommand(): CreateProductCommand =
    CreateProductCommand(
        name = name ?: throwNonNullableError("name"),
        price = price ?: throwNonNullableError("price"),
        description = description ?: throwNonNullableError("description"),
    )

fun Product.toDto(): ProductDto =
    ProductDto(
        id = id,
        name = name,
        description = description,
        price = price,
        createdDate = createdDate
    )

fun ProductDto.toResponse(): ProductResponse =
    ProductResponse(
        id = id,
        name = name,
        description = description,
        price = price,
        createdDate = createdDate
    )


private fun throwNonNullableError(field: String): Nothing {
  throw IllegalArgumentException("Field $field cannot be null!")
}