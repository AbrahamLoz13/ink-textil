package com.example.inktextil.ui.screens

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.util.Base64
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.ui.components.NavBar
import java.io.ByteArrayOutputStream

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun DisenoScreen(navController: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("Seleccionar") }
    var color by remember { mutableStateOf("Seleccionar") }
    var tipoCuello by remember { mutableStateOf("Seleccionar") }
    var largo by remember { mutableStateOf("Seleccionar") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var disenosGuardados by remember { mutableStateOf<List<DisenoLocal>>(emptyList()) }
    var mostrarDisenos by remember { mutableStateOf(true) }
    var editandoDiseno by remember { mutableStateOf<DisenoLocal?>(null) }

    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val imagenBitmap = remember(imageUri) {
        imageUri?.let {
            val source = ImageDecoder.createSource(context.contentResolver, it)
            ImageDecoder.decodeBitmap(source).asImageBitmap()
        }
    }

    fun limpiarCampos() {
        nombre = ""
        descripcion = ""
        talla = "Seleccionar"
        color = "Seleccionar"
        tipoCuello = "Seleccionar"
        largo = "Seleccionar"
        imageUri = null
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun guardarDiseno() {
        if (imageUri == null || nombre.isBlank() || descripcion.isBlank() ||
            talla == "Seleccionar" || color == "Seleccionar" || tipoCuello == "Seleccionar" || largo == "Seleccionar") {
            Toast.makeText(context, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
        val bitmap = ImageDecoder.decodeBitmap(source)

        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val imageBase64 = Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)

        val nuevo = DisenoLocal(nombre, descripcion, talla, color, tipoCuello, largo, imageBase64)
        disenosGuardados = disenosGuardados + nuevo

        limpiarCampos()
        editandoDiseno = null
        Toast.makeText(context, "Diseño guardado", Toast.LENGTH_SHORT).show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    fun actualizarDiseno() {
        if (editandoDiseno != null) {
            val imageBase64 = if (imageUri != null) {
                val source = ImageDecoder.createSource(context.contentResolver, imageUri!!)
                val bitmap = ImageDecoder.decodeBitmap(source)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                Base64.encodeToString(stream.toByteArray(), Base64.DEFAULT)
            } else {
                editandoDiseno!!.imagenBase64
            }

            val actualizado = DisenoLocal(nombre, descripcion, talla, color, tipoCuello, largo, imageBase64)
            disenosGuardados = disenosGuardados.map { if (it == editandoDiseno) actualizado else it }
            limpiarCampos()
            editandoDiseno = null
            Toast.makeText(context, "Diseño actualizado", Toast.LENGTH_SHORT).show()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 100.dp, top = 40.dp) // margen superior más grande
                .navigationBarsPadding()
                .verticalScroll(scrollState)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color(0xFF5472D3), shape = RoundedCornerShape(8.dp))
                        .clickable { launcher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        imagenBitmap != null -> {
                            Image(bitmap = imagenBitmap, contentDescription = null)
                        }
                        editandoDiseno != null -> {
                            val bytes = Base64.decode(editandoDiseno!!.imagenBase64, Base64.DEFAULT)
                            val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                            Image(bitmap = bitmap.asImageBitmap(), contentDescription = null)
                        }
                        else -> {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Seleccionar imagen",
                                tint = Color.White,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Dropdown("Talla", listOf("S", "M", "L", "XL"), talla) { talla = it }
            Dropdown("Color", listOf("Rojo", "Azul", "Negro", "Blanco"), color) { color = it }
            Dropdown("Cuello", listOf("Redondo", "V", "Cuello alto"), tipoCuello) { tipoCuello = it }
            Dropdown("Largo", listOf("Corto", "Medio", "Largo"), largo) { largo = it }

            Spacer(Modifier.height(16.dp))

            val botonColor = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2),
                contentColor = Color.White
            )

            if (editandoDiseno != null) {
                Button(onClick = { actualizarDiseno() }, modifier = Modifier.fillMaxWidth(), colors = botonColor) {
                    Text("Actualizar diseño")
                }
            } else {
                Button(onClick = { guardarDiseno() }, modifier = Modifier.fillMaxWidth(), colors = botonColor) {
                    Text("Guardar diseño")
                }
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = { mostrarDisenos = !mostrarDisenos },
                modifier = Modifier.fillMaxWidth(),
                colors = botonColor
            ) {
                Text(if (mostrarDisenos) "Ocultar diseños" else "Mostrar diseños")
            }

            if (mostrarDisenos && disenosGuardados.isNotEmpty()) {
                Spacer(Modifier.height(24.dp))
                Text("Diseños guardados:", fontSize = 18.sp)
                Spacer(Modifier.height(12.dp))

                disenosGuardados.forEach { d ->
                    val bytes = Base64.decode(d.imagenBase64, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF3FC))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text("Nombre: ${d.nombre}", fontSize = 16.sp)
                            Text("Descripción: ${d.descripcion}")
                            Text("Talla: ${d.talla}")
                            Text("Color: ${d.color}")
                            Text("Cuello: ${d.tipoCuello}")
                            Text("Largo: ${d.largo}")

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                IconButton(onClick = {
                                    nombre = d.nombre
                                    descripcion = d.descripcion
                                    talla = d.talla
                                    color = d.color
                                    tipoCuello = d.tipoCuello
                                    largo = d.largo
                                    imageUri = null
                                    editandoDiseno = d
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray)
                                }
                                IconButton(onClick = {
                                    disenosGuardados = disenosGuardados - d
                                    if (editandoDiseno == d) {
                                        limpiarCampos()
                                        editandoDiseno = null
                                    }
                                }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Gray)
                                }
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            NavBar(navController = navController)
        }
    }
}

@Composable
fun Dropdown(label: String, opciones: List<String>, seleccionActual: String, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 4.dp)) {
        Text(label)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(4.dp))
                .padding(12.dp)
        ) {
            Text(text = seleccionActual)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onSelected(opcion)
                        expanded = false
                    }
                )
            }
        }
    }
}

data class DisenoLocal(
    val nombre: String,
    val descripcion: String,
    val talla: String,
    val color: String,
    val tipoCuello: String,
    val largo: String,
    val imagenBase64: String
)
