package cz.cleevio.productapp.model.command

data class CreateProductCommand(
    val name: String,
    val description: String,
    val price: Double,
)
