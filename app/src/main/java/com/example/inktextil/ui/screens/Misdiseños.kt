package com.example.inktextil.ui.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.inktextil.data.Diseno
import com.example.inktextil.data.DisenoRepository
import com.example.inktextil.ui.components.NavBar
import kotlinx.coroutines.launch
@Composable
fun DisenoScreen(
    repository: DisenoRepository = DisenoRepository(),
    navController: NavController
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var descripcion by remember { mutableStateOf(TextFieldValue("")) }
    var imagenUri by remember { mutableStateOf<Uri?>(null) }
    var mostrarTarjeta by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imagenUri = uri
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp) // espacio para que el scroll no tape con navbar
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del diseño") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { launcher.launch("image/*") }) {
                Text("Seleccionar imagen")
            }

            Spacer(modifier = Modifier.height(16.dp))

            imagenUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Button(
                    onClick = {
                        if (nombre.text.isNotBlank() && descripcion.text.isNotBlank()) {
                            coroutineScope.launch {
                                try {
                                    val diseno = Diseno(
                                        nombre = nombre.text,
                                        descripcion = descripcion.text
                                    )
                                    repository.guardarDiseno(diseno)
                                    Toast.makeText(context, "Diseño guardado correctamente", Toast.LENGTH_SHORT).show()
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                        } else {
                            Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Guardar diseño")
                }

                Button(
                    onClick = { mostrarTarjeta = true },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Mostrar diseño")
                }
            }

            if (mostrarTarjeta && nombre.text.isNotBlank() && descripcion.text.isNotBlank() && imagenUri != null) {
                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(imagenUri),
                            contentDescription = "Diseño",
                            modifier = Modifier.size(200.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(text = "Nombre: ${nombre.text}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Descripción: ${descripcion.text}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(90.dp)) // espacio extra final
        }

        // NavBar fija abajo
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            NavBar(navController = navController)
        }
    }
}
