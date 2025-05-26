package com.example.inktextil.model

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _paymentMethods = MutableStateFlow<List<PaymentMethod>>(emptyList())
    val paymentMethods: StateFlow<List<PaymentMethod>> = _paymentMethods




    init {
        loadPaymentMethods()
    }

    /**
     * Carga los métodos de pago desde el documento de usuario en Firestore
     */
    fun loadPaymentMethods() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("usuarios").document(uid)
            .get()
            .addOnSuccessListener { document ->
                val usuario = document.toObject(UsuarioData::class.java)
                val metodos = usuario?.metodosPago ?: emptyList()
                _paymentMethods.value = metodos
            }
            .addOnFailureListener {
                Log.e("PaymentViewModel", "Error al cargar métodos de pago", it)
            }
    }

    /**
     * Agrega un método de pago nuevo y lo guarda en Firestore
     */
    fun addPaymentMethod(method: PaymentMethod) {
        val uid = auth.currentUser?.uid ?: return
        val nuevos = _paymentMethods.value.toMutableList().apply {
            add(method)
        }

        db.collection("usuarios").document(uid)
            .update("metodosPago", nuevos)
            .addOnSuccessListener {
                _paymentMethods.value = nuevos
            }
            .addOnFailureListener {
                // Si el documento no existe aún, lo crea
                db.collection("usuarios").document(uid)
                    .set(mapOf("metodosPago" to nuevos))
                    .addOnSuccessListener {
                        _paymentMethods.value = nuevos
                    }
                    .addOnFailureListener { e ->
                        Log.e("PaymentViewModel", "Error al guardar nuevo método", e)
                    }
            }
    }

    /**
     * Elimina un método de pago de la lista
     */
    fun deletePaymentMethod(cardNumber: String) {
        val uid = auth.currentUser?.uid ?: return
        val filtrados = _paymentMethods.value.filter { it.cardNumber != cardNumber }

        db.collection("usuarios").document(uid)
            .update("metodosPago", filtrados)
            .addOnSuccessListener {
                _paymentMethods.value = filtrados
            }
            .addOnFailureListener {
                Log.e("PaymentViewModel", "Error al eliminar método", it)
            }
    }
}
