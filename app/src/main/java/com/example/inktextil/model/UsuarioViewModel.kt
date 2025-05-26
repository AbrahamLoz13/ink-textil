package com.example.inktextil.model

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UsuarioViewModel : ViewModel() {

    private val db = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    // Estado principal del usuario
    var usuarioData by mutableStateOf(UsuarioData())
        private set

    // Estado de pedido confirmado (para pantalla de pago)
    var pedidoConfirmado by mutableStateOf<Pedido?>(null)
        private set

    // Historial de pedidos
    var historialPedidos by mutableStateOf<List<Pedido>>(emptyList())
        private set

    // --- 🔹 Cargar datos usuario ---
    fun cargarDatosUsuario() {
        val uid = auth.currentUser?.uid ?: return
        val user = auth.currentUser

        db.collection("usuarios").document(uid).get()
            .addOnSuccessListener { document ->
                usuarioData = if (document.exists()) {
                    document.toObject(UsuarioData::class.java) ?: UsuarioData()
                } else {
                    UsuarioData(
                        username = user?.displayName ?: "",
                        correo = user?.email ?: ""
                    )
                }
            }
    }

    // --- 🔹 Guardar campos modificados ---
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

    fun guardarDatosUsuario() {
        val uid = auth.currentUser?.uid ?: return
        db.collection("usuarios").document(uid).set(usuarioData)
    }

    // --- 🔹 Direcciones ---
    fun agregarDireccion(direccion: Direccion) {
        val nuevasDirecciones = usuarioData.direcciones.toMutableList().apply {
            add(direccion)
        }
        usuarioData = usuarioData.copy(direcciones = nuevasDirecciones)

        val uid = auth.currentUser?.uid ?: return
        db.collection("usuarios").document(uid)
            .update("direcciones", nuevasDirecciones)
            .addOnFailureListener {
                // Si no existe el campo direcciones aún
                db.collection("usuarios").document(uid)
                    .set(usuarioData)
            }
    }


    fun seleccionarDireccion(index: Int) {
        usuarioData = usuarioData.copy(direccionSeleccionada = index)
        guardarDatosUsuario()
    }

    // --- 🔹 Métodos de pago ---
    fun agregarMetodoPago(metodo: PaymentMethod) {
        val uid = auth.currentUser?.uid ?: return
        val nuevos = usuarioData.metodosPago.toMutableList().apply { add(metodo) }
        db.collection("usuarios").document(uid)
            .update("metodosPago", nuevos)
            .addOnSuccessListener { cargarDatosUsuario() }
        usuarioData = usuarioData.copy(metodosPago = nuevos)
    }

    fun eliminarMetodoPago(ultimos4: String) {
        val filtrados = usuarioData.metodosPago.filterNot { it.lastFourDigits == ultimos4 }
        usuarioData = usuarioData.copy(metodosPago = filtrados)
        guardarDatosUsuario()
    }

    fun seleccionarMetodoPago(index: Int) {
        usuarioData = usuarioData.copy(metodoPagoSeleccionado = index)
        guardarDatosUsuario()
    }

    // --- 🔹 Confirmar y guardar pedido ---
    fun confirmarPedido(
        productos: List<ShirtItem>,
        direccion: Direccion,
        metodo: PaymentMethod,
        total: Double,
        onSuccess: (() -> Unit)? = null,
        onError: (() -> Unit)? = null
    ) {
        val pedido = Pedido(
            productos = productos,
            direccion = direccion,
            metodoPago = metodo,
            total = total
        )
        pedidoConfirmado = pedido

        val uid = auth.currentUser?.uid ?: return
        val ref = db.collection("usuarios").document(uid).collection("pedidos").document()
        val pedidoConId = pedido.copy(id = ref.id)

        ref.set(pedidoConId)
            .addOnSuccessListener {
                onSuccess?.invoke()
            }
            .addOnFailureListener {
                onError?.invoke()
            }
    }

    fun guardarPedido(pedido: Pedido, onSuccess: (String) -> Unit, onError: () -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return onError()

        val docRef = Firebase.firestore
            .collection("usuarios")
            .document(uid)
            .collection("pedidos")
            .document() // genera ID

        val id = docRef.id
        val pedidoConId = pedido.copy(id = id)

        docRef.set(pedidoConId)
            .addOnSuccessListener {
                onSuccess(id) // 🚨 MUY IMPORTANTE
            }
            .addOnFailureListener {
                onError()
            }
    }



    fun cargarHistorialPedidos() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        Firebase.firestore
            .collection("usuarios")
            .document(uid)
            .collection("pedidos")
            .get()
            .addOnSuccessListener { result ->
                val pedidosList = result.documents.mapNotNull { it.toObject(Pedido::class.java) }
                historialPedidos = pedidosList
            }
    }



}
