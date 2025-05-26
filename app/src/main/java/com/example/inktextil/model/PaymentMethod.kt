package com.example.inktextil.model

data class PaymentMethod(
    val nameOnCard: String = "",
    val cardNumber: String = "", // Opcional en producción por seguridad
    val lastFourDigits: String = "",
    val expiryMonth: String = "",
    val expiryYear: String = "",
    val address: String = ""
)



