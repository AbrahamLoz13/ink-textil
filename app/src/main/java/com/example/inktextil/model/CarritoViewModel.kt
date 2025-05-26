package com.example.inktextil.ui.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.inktextil.model.ShirtItem
import com.example.inktextil.ui.screens.JacketItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CarritoViewModel : ViewModel() {

    // Lista para el carrito
    private val _carrito = mutableStateListOf<ShirtItem>()
    val carrito: List<ShirtItem> get() = _carrito

    // Lista para la wishlist
    private val _wishlist = mutableStateListOf<ShirtItem>()
    val wishlist: List<ShirtItem> get() = _wishlist

    private val _total = MutableStateFlow(0.0)
    val total: StateFlow<Double> get() = _total

    // Funciones de carrito
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

    // Funciones de wishlist
    fun agregarAWishlist(shirt: ShirtItem) {
        _wishlist.add(shirt)
    }

    fun eliminarDeWishlist(shirt: ShirtItem) {
        _wishlist.remove(shirt)
    }

    // Función toggle para agregar o eliminar de la wishlist
    fun toggleWishlist(shirt: ShirtItem) {
        if (_wishlist.contains(shirt)) {
            eliminarDeWishlist(shirt) // Si está en la wishlist, lo eliminamos
        } else {
            agregarAWishlist(shirt) // Si no está en la wishlist, lo agregamos
        }
    }

    private fun calcularTotal() {
        val totalCalculado = _carrito.sumOf { item ->
            try {
                val priceString = item.price?.toString() ?: "0.0"
                priceString.replace(Regex("[^\\d.]"), "").toDoubleOrNull() ?: 0.0
            } catch (e: Exception) {
                0.0
            }
        }
        _total.update { totalCalculado }
    }


}

