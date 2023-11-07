package cz.cleevio.productapp.controller

import cz.cleevio.productapp.constant.Mapping.PRODUCT
import cz.cleevio.productapp.mapper.toCreateProductCommand
import cz.cleevio.productapp.mapper.toResponse
import cz.cleevio.productapp.model.request.ProductRequest
import cz.cleevio.productapp.model.response.ProductResponse
import cz.cleevio.productapp.service.ProductService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import mu.KotlinLogging.logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus

@RestController
class ProductController(
  val productService: ProductService
) {

  private val log = logger {}

  @GetMapping(PRODUCT, produces = [MediaType.APPLICATION_JSON_VALUE])
  fun getProducts(): List<ProductResponse> {
    return productService.getAllProducts()
        .map{ it.toResponse() }
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping(PRODUCT, consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
  fun createProduct(@RequestBody request: ProductRequest): ProductResponse {
    return request
        .toCreateProductCommand()
        .run { productService.addProduct(this) }
        .toResponse()
  }
}