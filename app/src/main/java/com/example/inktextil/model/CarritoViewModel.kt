package com.example.inktextil.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CarritoViewModel : ViewModel() {

    private val _carrito = mutableStateListOf<ShirtItem>()
    val carrito: List<ShirtItem> get() = _carrito

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> get() = _total

    fun agregarAlCarrito(shirt: ShirtItem) {
        _carrito.add(shirt)
        calcularTotal()
    }

    fun eliminarDelCarrito(shirt: ShirtItem) {
        _carrito.remove(shirt)
        calcularTotal()
    }

    fun limpiarCarrito() {
        _carrito.clear()
        calcularTotal()
    }

    fun obtenerCarrito() {
        calcularTotal()
    }

    private fun calcularTotal() {
        val totalCalculado = _carrito.sumOf {
            it.price.replace("$", "").replace("MXN", "").trim().toDoubleOrNull() ?: 0.0
        }
        _total.update { totalCalculado }
    }
}
