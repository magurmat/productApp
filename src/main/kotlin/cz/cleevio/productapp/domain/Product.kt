package cz.cleevio.productapp.domain


import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant


const val PRODUCT = "product"


@Document(collection = PRODUCT)
data class Product(

    @Id
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val createdDate: Instant = Instant.now()

)