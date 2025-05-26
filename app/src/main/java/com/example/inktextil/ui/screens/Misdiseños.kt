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
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.inktextil.data.Diseno
import com.example.inktextil.data.DisenoRepository
import com.example.inktextil.ui.components.NavBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    var talla by remember { mutableStateOf("M") }
    var cuello by remember { mutableStateOf("Redondo") }
    var corte by remember { mutableStateOf("Regular") }

    var disenosGuardados by remember { mutableStateOf<List<Diseno>>(emptyList()) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imagenUri = uri
    }

    val tallas = listOf("S", "M", "L", "XL")
    val cuellos = listOf("Redondo", "V", "Cuello Alto")
    val cortes = listOf("Regular", "Slim", "Oversize")

    val calcularPrecio: () -> Double = {
        var base = 250.0
        if (talla == "L") base += 50
        if (talla == "XL") base += 80
        if (cuello == "Cuello Alto") base += 30
        if (corte == "Slim") base += 40
        if (corte == "Oversize") base += 20
        base
    }

    // Cargar diseños guardados al iniciar
    LaunchedEffect(Unit) {
        disenosGuardados = repository.obtenerDisenosUsuario()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
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

            imagenUri?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                Column {
                    Text("Talla", fontSize = 14.sp)
                    tallas.forEach {
                        FilterChip(selected = talla == it, onClick = { talla = it }, label = { Text(it) })
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Cuello", fontSize = 14.sp)
                    cuellos.forEach {
                        FilterChip(selected = cuello == it, onClick = { cuello = it }, label = { Text(it) })
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Corte", fontSize = 14.sp)
                    cortes.forEach {
                        FilterChip(selected = corte == it, onClick = { corte = it }, label = { Text(it) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = {
                if (nombre.text.isNotBlank() && descripcion.text.isNotBlank() && imagenUri != null) {
                    coroutineScope.launch {
                        try {
                            val diseno = Diseno(
                                nombre = nombre.text,
                                descripcion = descripcion.text,
                                talla = talla,
                                cuello = cuello,
                                corte = corte,
                                precio = calcularPrecio()
                            )
                            repository.guardarDiseno(diseno, imagenUri!!, context)
                            Toast.makeText(context, "Diseño guardado con éxito", Toast.LENGTH_SHORT).show()
                            disenosGuardados = repository.obtenerDisenosUsuario()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Guardar diseño ($${calcularPrecio()} MXN)")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Tus diseños", style = MaterialTheme.typography.titleLarge)

            disenosGuardados.forEach { d ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(d.imagenUrl),
                            contentDescription = null,
                            modifier = Modifier.height(160.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Nombre: ${d.nombre}", fontWeight = FontWeight.Bold)
                        Text("Descripción: ${d.descripcion}")
                        Text("Talla: ${d.talla}, Cuello: ${d.cuello}, Corte: ${d.corte}")
                        Text("Precio: $${d.precio} MXN", fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }

        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            NavBar(navController)
        }
    }
}
