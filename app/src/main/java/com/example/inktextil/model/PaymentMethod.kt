package com.example.inktextil.model

data class PaymentMethod(
    val nameOnCard: String = "",
    val expiryMonth: String = "",
    val expiryYear: String = "",
    val address: String = "",
    val lastFourDigits: String = ""
)
