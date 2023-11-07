package cz.cleevio.productapp.service.impl

import cz.cleevio.productapp.domain.Product
import cz.cleevio.productapp.mapper.toDto
import cz.cleevio.productapp.model.command.CreateProductCommand
import cz.cleevio.productapp.model.dto.ProductDto
import cz.cleevio.productapp.repository.ProductRepository
import cz.cleevio.productapp.service.ProductService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository
): ProductService {
  override fun getAllProducts(): List<ProductDto> {
    return productRepository.findAll().map { it.toDto() }
  }

  override fun addProduct(command: CreateProductCommand): ProductDto {
    return createProduct(command)
        .run { productRepository.save(this) }
        .toDto()
  }

  private fun createProduct(command: CreateProductCommand): Product = with(command) {
    Product(
        id = UUID.randomUUID().toString(),
        name = name,
        description = description,
        price = price
    )
  }
}