package com.example.inktextil.model

import androidx.annotation.Keep

data class Articulo(
    val nombre: String = "",
    val descripcion: String = "",
    val imagenUrl: String = "",
    val tallas: String = "",
    val color: String = "",
    val precio: String = "",
    val tipo: String = "" // hoodie, jacket, pants, shirt...
)

@Keep
data class ShirtItem(
    val title: String = "",
    val description: String = "",
    val size: String = "",
    val color: String = "",
    val price: String = "",
    val imageRes: Int = 0
)

