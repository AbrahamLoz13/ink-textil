package com.example.inktextil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
        TopBar(navController = navController)

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Campo de Username y Botón
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = "",
                            onValueChange = {},
                            placeholder = { Text("Username") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color(0xFFFDFDFD),
                                focusedIndicatorColor = Color(0xFF6200EE),
                                unfocusedIndicatorColor = Color(0xFFBDBDBD)
                            )
                        )
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6200EE),
                                contentColor = Color.White
                            )
                        ) {
                            Text("Guardar")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campos
                    CustomTextField("Correo")
                    CustomTextField("Dirección 1")
                    CustomTextField("Dirección 2")
                    DropdownMenuField("Ciudad", listOf("Ciudad A", "Ciudad B", "Ciudad C"))
                    CustomTextField("C.p")
                    DropdownMenuField("Colonia", listOf("Colonia X", "Colonia Y", "Colonia Z"))
                    CustomTextField("Número")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Barra de navegación fija en la parte inferior
        NavBar(navController = navController)
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
                .menuAnchor(),
            readOnly = true,
            value = selectedText,
            onValueChange = {},
            label = { Text(label) },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0xFFFDFDFD),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(label: String) {
    TextField(
        value = "",
        onValueChange = {},
        placeholder = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color(0xFFFDFDFD),
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
