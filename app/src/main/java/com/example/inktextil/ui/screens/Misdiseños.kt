package com.example.inktextil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@Composable
fun DisenoScreen(navController: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var talla by remember { mutableStateOf("Seleccionar") }
    var color by remember { mutableStateOf("Seleccionar") }
    var tipoCuello by remember { mutableStateOf("Seleccionar") }
    var largo by remember { mutableStateOf("Seleccionar") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(text = "MI DISEÑO", fontSize = 20.sp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_gallery),
                        contentDescription = "Imagen",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                DropdownButton("Talla", listOf("S", "M", "L", "XL"), talla) { talla = it }
                DropdownButton("Color", listOf("Rojo", "Azul", "Negro", "Blanco"), color) { color = it }
                DropdownButton("Tipo de cuello", listOf("Redondo", "V", "Cuello alto"), tipoCuello) { tipoCuello = it }
                DropdownButton("Largo", listOf("Corto", "Medio", "Largo"), largo) { largo = it }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Button(onClick = { /* Guardar lógica */ }) {
                    Text("Guardar")
                }
                Button(onClick = { /* Eliminar lógica */ }, colors = ButtonDefaults.buttonColors(Color.Red)) {
                    Text("Eliminar")
                }


            }

            Spacer(modifier = Modifier.weight(1f))
            NavBar(navController)

        }



    }


}

@Composable
fun DropdownButton(label: String, options: List<String>, selected: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 14.sp, color = Color.Black)
        Button(onClick = { expanded = true }, modifier = Modifier.fillMaxWidth()) {
            Text(selected)
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    onOptionSelected(option)
                    expanded = false
                })
            }
        }
    }
}

@Preview
@Composable
fun PreviewDisenoScreen() {
    DisenoScreen(navController = NavHostController(LocalContext.current))
}
