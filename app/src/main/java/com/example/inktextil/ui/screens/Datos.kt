package com.example.inktextil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "DATOS",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar") },
                modifier = Modifier.width(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {},
            modifier = Modifier.align(alignment = androidx.compose.ui.Alignment.End)
        ) {
            Text("Guardar")
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
            placeholder = { Text("Dirección 1") },
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
            placeholder = { Text("C.P") },
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

        BottomNavigationBars()
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

@Composable
fun BottomNavigationBars() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text("Inicio", color = Color.White)
        Text("Cuenta", color = Color.White)
        Text("Carrito", color = Color.White)
        Text("Menú", color = Color.White)
    }
}


@Composable
fun DatosScreenPreview() {
    DatosScreen()
}

