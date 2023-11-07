package cz.cleevio.productapp.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant

@ControllerAdvice
class RestExceptionHandler {

  @ExceptionHandler(IllegalArgumentException::class)
  fun handleIllegalArgument(e: IllegalArgumentException): ResponseEntity<ApiException> {
    val apiException = ApiException(
        status = HttpStatus.BAD_REQUEST,
        message = e.message,
        timestamp = Instant.now()
    )
    return ResponseEntity(apiException, HttpStatus.BAD_REQUEST)
  }

}