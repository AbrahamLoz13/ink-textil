package com.example.inktextil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar
        TopBar(navController = navController)

        // Contenido principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Username") },
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {},
                ) {
                    Text("Guardar")
                }
            }

            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Direccion 1") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Dirección 2") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenuField(label = "Ciudad", items = listOf("Ciudad A", "Ciudad B", "Ciudad C"))

            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("C.p") },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenuField(label = "Colonia", items = listOf("Colonia X", "Colonia Y", "Colonia Z"))

            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Número") },
                modifier = Modifier.fillMaxWidth()
            )

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
                .menuAnchor(),
            readOnly = true,
            value = selectedText,
            onValueChange = {},
            label = { Text(label) },
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