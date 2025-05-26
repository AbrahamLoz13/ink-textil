package com.example.inktextil.data


import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.util.UUID

class DisenoRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    suspend fun guardarDiseno(diseno: Diseno, imagenUri: Uri, context: Context) {
        val user = auth.currentUser ?: throw Exception("Usuario no autenticado")

        // 🔹 1. Generar nombre único para la imagen
        val fileName = UUID.randomUUID().toString()
        val storageRef = storage.reference
            .child("disenos/${user.uid}/$fileName.jpg")

        // 🔹 2. Abrir InputStream de forma segura
        val stream = try {
            context.contentResolver.openInputStream(imagenUri)
        } catch (e: Exception) {
            null
        }

        if (stream == null) {
            throw Exception("No se pudo leer la imagen. Intenta seleccionar otra.")
        }

        // 🔹 3. Subir imagen
        val uploadTask = storageRef.putStream(stream).await()

        // 🔹 4. Obtener URL de la imagen subida
        val downloadUrl = storageRef.downloadUrl.await()

        // 🔹 5. Guardar en Firestore
        val disenoMap = hashMapOf(
            "nombre" to diseno.nombre,
            "descripcion" to diseno.descripcion,
            "talla" to diseno.talla,
            "cuello" to diseno.cuello,
            "corte" to diseno.corte,
            "precio" to diseno.precio,
            "imagenUrl" to downloadUrl.toString()
        )

        firestore.collection("disenos")
            .document(user.uid)
            .collection("items")
            .add(disenoMap)
            .await()
    }

    suspend fun obtenerDisenosUsuario(): List<Diseno> {
        val user = FirebaseAuth.getInstance().currentUser ?: return emptyList()

        val snapshot = FirebaseFirestore.getInstance()
            .collection("disenos")
            .document(user.uid)
            .collection("items")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            val data = doc.data ?: return@mapNotNull null
            Diseno(
                nombre = data["nombre"] as? String ?: "",
                descripcion = data["descripcion"] as? String ?: "",
                talla = data["talla"] as? String ?: "",
                cuello = data["cuello"] as? String ?: "",
                corte = data["corte"] as? String ?: "",
                precio = (data["precio"] as? Number)?.toDouble() ?: 0.0,
                imagenUrl = data["imagenUrl"] as? String ?: ""
            )
        }
    }
}
