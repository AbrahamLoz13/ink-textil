package com.example.inktextil.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Diseno(
    val userId: Int = 1,
    @SerialName("title") val nombre: String,
    @SerialName("body") val descripcion: String
)
