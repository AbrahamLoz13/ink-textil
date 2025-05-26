package com.example.inktextil.model




data class Direccion(
    val calle: String = "",
    val numero: String = "",
    val colonia: String = "",
    val ciudad: String = "",
    val codigoPostal: String = "",
    val nombreReceptor: String = "",
    val numeroTelefono: String = ""
)






data class UsuarioData(
    val username: String = "",
    val correo: String = "",

    // Datos sueltos (legacy o backup)
    val direccion1: String = "",
    val direccion2: String = "",
    val ciudad: String = "",
    val cp: String = "",
    val colonia: String = "",
    val numero: String = "",

    // Datos compuestos
    val direcciones: List<Direccion> = emptyList(),
    val direccionSeleccionada: Int? = null,

    val metodosPago: List<PaymentMethod> = emptyList(),
    val metodoPagoSeleccionado: Int? = null
)
