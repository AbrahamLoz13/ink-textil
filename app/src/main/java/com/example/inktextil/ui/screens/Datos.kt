package com.example.inktextil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar
        TopBar(navController = navController)

        // Contenido principal con fondo de color suave
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(Color(0xFFF1F1F1)), // Fondo suave
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Input de Username y Botón Guardar con diseño atractivo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Username", color = Color.Gray) },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp), // Bordes redondeados
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF6200EE),
                        unfocusedIndicatorColor = Color(0xFFBDBDBD)
                    )
                )
                Button(
                    onClick = {},
                    modifier = Modifier
                        .padding(8.dp)
                        .height(50.dp)
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(12.dp), // Bordes redondeados
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6200EE),
                        contentColor = Color.White
                    )
                ) {
                    Text("Guardar", style = MaterialTheme.typography.bodyLarge)
                }
            }

            // Campos de texto con bordes suaves y foco atractivo
            CustomTextField(label = "Correo")
            CustomTextField(label = "Direccion 1")
            CustomTextField(label = "Dirección 2")

            // Dropdowns mejorados con diseño
            DropdownMenuField(label = "Ciudad", items = listOf("Ciudad A", "Ciudad B", "Ciudad C"))
            CustomTextField(label = "C.p")
            DropdownMenuField(label = "Colonia", items = listOf("Colonia X", "Colonia Y", "Colonia Z"))
            CustomTextField(label = "Número")

            Spacer(modifier = Modifier.weight(1f))
            // NavBar
            NavBar(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(label: String, items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(items.first()) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .padding(8.dp),
            readOnly = true,
            value = selectedText,
            onValueChange = {},
            label = { Text(label, color = Color.Gray) },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF6200EE),
                unfocusedIndicatorColor = Color(0xFFBDBDBD)
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { selection ->
                DropdownMenuItem(
                    text = { Text(selection) },
                    onClick = {
                        selectedText = selection
                        expanded = false
                    }
                )
            }
        }
    }
}

// Componente para un TextField mejorado con bordes y colores
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(label: String) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(label, color = Color.Gray) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            focusedIndicatorColor = Color(0xFF6200EE),
            unfocusedIndicatorColor = Color(0xFFBDBDBD)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun DatosScreenPreview() {
    DatosScreen(navController = rememberNavController())
}
