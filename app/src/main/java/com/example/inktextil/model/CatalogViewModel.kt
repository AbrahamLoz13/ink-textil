package com.example.inktextil.model

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class CatalogoViewModel(private val tipo: String) : ViewModel() {

    private val _articulos = mutableStateListOf<Articulo>()
    val articulos: List<Articulo> = _articulos

    init {
        FirebaseFirestore.getInstance().collection("articulos")
            .whereEqualTo("tipo", tipo)
            .get()
            .addOnSuccessListener { result ->
                _articulos.clear()
                result.forEach { doc ->
                    _articulos.add(doc.toObject(Articulo::class.java))
                }
            }
    }
}
