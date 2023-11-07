package cz.cleevio.productapp.service

import cz.cleevio.productapp.model.command.CreateProductCommand
import cz.cleevio.productapp.model.dto.ProductDto

interface ProductService {
  fun getAllProducts(): List<ProductDto>
  fun addProduct(command: CreateProductCommand): ProductDto
}