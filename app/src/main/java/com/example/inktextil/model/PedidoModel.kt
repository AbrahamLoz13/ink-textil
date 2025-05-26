package com.example.inktextil.model



data class Pedido(
    val id: String = "",
    val articulos: List<ShirtItem> = emptyList(),
    val direccion: Direccion = Direccion(),
    val metodoPago: PaymentMethod = PaymentMethod(),
    val total: Double = 0.0,
    val fecha: Long = System.currentTimeMillis(),
    val productos: List<ShirtItem> = emptyList(),

    )
