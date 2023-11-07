package cz.cleevio.productapp.controller.advice

import org.springframework.http.HttpStatus
import java.time.Instant

data class ApiException(
    val status: HttpStatus,
    val message: String?,
    val timestamp: Instant
)
