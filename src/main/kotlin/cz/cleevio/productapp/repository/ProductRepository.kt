package cz.cleevio.productapp.repository

import cz.cleevio.productapp.domain.Product
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: MongoRepository<Product, String> {
  override fun findAll(): MutableList<Product>
}