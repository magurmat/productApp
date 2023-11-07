package cz.cleevio.productapp.mapper

import cz.cleevio.productapp.model.request.ProductRequest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import kotlin.IllegalArgumentException

class ProductMapperTest {

  @Test
  fun whenMappingFilledRequest_ReturnsCreateCommand(){
    val request = ProductRequest(name = "name", description = "des", price = 0.0)
    val command = request.toCreateProductCommand()
    Assertions.assertEquals(request.name, command.name)
    Assertions.assertEquals(request.description, command.description)
    Assertions.assertEquals(request.price, command.price)
  }

  @Test
  fun whenMappingRequestWithNullName_ThrowsException(){
    val request = ProductRequest(name = null, description = "des", price = 0.0)
    try {
      request.toCreateProductCommand()
      assert(false)
    } catch (e: IllegalArgumentException) {
      Assertions.assertEquals("Field name cannot be null!", e.message)
    }
  }

  @Test
  fun whenMappingRequestWithNullDescription_ThrowsException(){
    val request = ProductRequest(name = "name", description = null, price = 0.0)
    try {
      request.toCreateProductCommand()
      assert(false)
    } catch (e: IllegalArgumentException) {
      Assertions.assertEquals("Field description cannot be null!", e.message)
    }
  }

  @Test
  fun whenMappingRequestWithNullPrice_ThrowsException(){
    val request = ProductRequest(name = "name", description = "des", price = null)
    try {
      request.toCreateProductCommand()
      assert(false)
    } catch (e: IllegalArgumentException) {
      Assertions.assertEquals("Field price cannot be null!", e.message)
    }
  }
}