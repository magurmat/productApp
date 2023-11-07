package cz.cleevio.productapp.service

import cz.cleevio.productapp.domain.Product
import cz.cleevio.productapp.model.command.CreateProductCommand
import cz.cleevio.productapp.repository.ProductRepository
import cz.cleevio.productapp.service.impl.ProductServiceImpl
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito

class ProductServiceTest {
  val productRepository = Mockito.mock(ProductRepository::class.java)
  val productService = ProductServiceImpl(productRepository)

  @Test
  fun whenCreateProduct_thenReturnProductDto() {
    val command = CreateProductCommand(name = "test", description = "test", price = 0.0)

    Mockito.`when`(productRepository.save(Mockito.argThat { it.name == command.name && it.price == command.price && it.description == command.description }))
        .thenAnswer { invocation ->
          val savedProduct = invocation.arguments[0] as Product
          Product(id = "1", name = savedProduct.name, price = savedProduct.price, description = savedProduct.description)
        }

    val createdProduct = productService.addProduct(command)
    Assertions.assertEquals(createdProduct.name, command.name)
    Assertions.assertEquals(createdProduct.price, command.price)
    Assertions.assertEquals(createdProduct.description, command.description)
  }

  @Test
  fun whenGetAllProducts_thenReturnAllProductsDto() {
    Mockito.`when`(productRepository.findAll())
        .thenAnswer {
          listOf(
              Product(id = "1", name = "name1", price = 1.0, description = "desc1"),
              Product(id = "2", name = "name2", price = 2.0, description = "desc2"),
              Product(id = "4", name = "name4", price = 3.0, description = "desc3"),
          )
        }
    val allProducts = productService.getAllProducts()
    Assertions.assertEquals(allProducts.size, 3)
    Assertions.assertEquals(allProducts.filter { it.name == "name1" }.size, 1)
    Assertions.assertEquals(allProducts.filter { it.name == "name2" }.size, 1)
    Assertions.assertEquals(allProducts.filter { it.name == "name4" }.size, 1)
  }
}