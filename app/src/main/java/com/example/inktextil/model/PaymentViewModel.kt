package com.example.inktextil.model


import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PaymentViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _paymentMethods = MutableStateFlow<List<PaymentMethod>>(emptyList())
    val paymentMethods: StateFlow<List<PaymentMethod>> = _paymentMethods

    init {
        fetchPaymentMethods()
    }

    fun fetchPaymentMethods() {
        userId?.let { uid ->
            db.collection("users").document(uid).collection("payment_methods")
                .addSnapshotListener { snapshot, _ ->
                    val methods = snapshot?.documents?.mapNotNull {
                        it.toObject(PaymentMethod::class.java)
                    } ?: emptyList()
                    _paymentMethods.value = methods
                }
        }
    }

    fun addPaymentMethod(method: PaymentMethod) {
        userId?.let { uid ->
            db.collection("users").document(uid).collection("payment_methods")
                .add(method)
        }
    }

    fun deletePaymentMethod(lastFourDigits: String) {
        userId?.let { uid ->
            db.collection("users").document(uid).collection("payment_methods")
                .whereEqualTo("lastFourDigits", lastFourDigits)
                .get().addOnSuccessListener { snapshot ->
                    snapshot.documents.forEach { it.reference.delete() }
                }
        }
    }
}
