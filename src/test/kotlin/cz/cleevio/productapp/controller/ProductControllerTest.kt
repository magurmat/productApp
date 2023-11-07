package cz.cleevio.productapp.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import cz.cleevio.productapp.constant.Mapping
import cz.cleevio.productapp.mapper.toCreateProductCommand
import cz.cleevio.productapp.mapper.toResponse
import cz.cleevio.productapp.model.command.CreateProductCommand
import cz.cleevio.productapp.model.dto.ProductDto
import cz.cleevio.productapp.model.request.ProductRequest
import cz.cleevio.productapp.model.response.ProductResponse
import cz.cleevio.productapp.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.servlet.function.RequestPredicates.contentType
import java.time.Instant


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @Autowired
  lateinit var mockMvc: MockMvc

  @MockBean
  private lateinit var productService: ProductService

  private val objectMapper = ObjectMapper().registerModule(JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).setDateFormat(StdDateFormat())

  @Test
  fun whenGettingAllProducts_ReturnsAllProducts() {
    val productList = listOf(
        ProductDto(id = "1", name = "name1", price = 1.0, description = "desc1", createdDate = Instant.now()),
        ProductDto(id = "2", name = "name2", price = 2.0, description = "desc2", createdDate = Instant.now()),
        ProductDto(id = "4", name = "name4", price = 3.0, description = "desc3", createdDate = Instant.now()),
    )
    val productResponseList = productList.map{ it.toResponse() }
    Mockito.`when`(productService.getAllProducts())
        .thenAnswer{ productList }

    mockMvc.get(Mapping.PRODUCT)
        .andDo { print() }
        .andExpect {
          status { isOk() }
          content {
            contentType(MediaType.APPLICATION_JSON)
            json(objectMapper.writeValueAsString(productResponseList))
          }
        }

  }

  @Test
  fun whenCreatingProductWithAllData_ReturnsNewProduct() {
    val createdTime = Instant.now()
    val request = ProductRequest(name = "name", description = "des", price = 0.0)
    val command = CreateProductCommand(name = request.name!!, description = request.description!!, price = request.price!!)
    val response = ProductResponse(name = request.name!!, description = request.description!!, price = request.price!!, id = "1", createdDate = createdTime)

    Mockito.`when`(productService.addProduct(command))
        .thenAnswer{ProductDto(id = "1", name = request.name!!, price = request.price!!, description = request.description!!, createdDate = createdTime)}

    mockMvc.post(Mapping.PRODUCT) {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsString(request)
    }
        .andDo { print() }
        .andExpect {
          status { isCreated() }
          content {
            contentType(MediaType.APPLICATION_JSON)
            json(objectMapper.writeValueAsString(response))
          }
        }
  }

  @Test
  fun whenCreatingProductWithMissingRequiredField_Return400() {
    val request = ProductRequest(name = "name", description = "des", price = null)


    mockMvc.post(Mapping.PRODUCT) {
      contentType = MediaType.APPLICATION_JSON
      content = objectMapper.writeValueAsString(request)
    }
        .andDo { print() }
        .andExpect {
          status { isBadRequest() }
        }
  }
}