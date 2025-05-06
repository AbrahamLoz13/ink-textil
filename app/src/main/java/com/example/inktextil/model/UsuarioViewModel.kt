package com.example.inktextil.model


import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.inktextil.model.UsuarioData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UsuarioViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()
    var usuarioData by mutableStateOf(UsuarioData())
        private set

    fun cargarDatosUsuario() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("usuarios").document(uid).get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    usuarioData = document.toObject(UsuarioData::class.java) ?: UsuarioData()
                }
            }
    }

    fun guardarDatosUsuario() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("usuarios").document(uid).set(usuarioData)
    }

    fun actualizarCampo(campo: String, valor: String) {
        usuarioData = when (campo) {
            "username" -> usuarioData.copy(username = valor)
            "correo" -> usuarioData.copy(correo = valor)
            "direccion1" -> usuarioData.copy(direccion1 = valor)
            "direccion2" -> usuarioData.copy(direccion2 = valor)
            "ciudad" -> usuarioData.copy(ciudad = valor)
            "cp" -> usuarioData.copy(cp = valor)
            "colonia" -> usuarioData.copy(colonia = valor)
            "numero" -> usuarioData.copy(numero = valor)
            else -> usuarioData
        }
    }
}
