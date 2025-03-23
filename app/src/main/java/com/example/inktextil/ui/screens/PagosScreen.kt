package com.example.inktextil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@Composable
fun PagosScreen(navController: NavHostController) {
    var selectedCard by remember { mutableStateOf<String?>(null) }
    var nameOnCard by remember { mutableStateOf("") }
    var expiryMonth by remember { mutableStateOf("01") }
    var expiryYear by remember { mutableStateOf("2025") }
    var address by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("TARJETAS Y CUENTAS", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(3) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                            .clickable { selectedCard = "Card $index" }
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Tarjeta **** **** **** ${1000 + index}")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = nameOnCard,
                onValueChange = { nameOnCard = it },
                label = { Text("Nombre en la tarjeta") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                DropdownMenuField(label = "Mes", options = (1..12).map { it.toString().padStart(2, '0') }, selectedOption = expiryMonth) { expiryMonth = it }
                DropdownMenuField(label = "Año", options = (2025..2035).map { it.toString() }, selectedOption = expiryYear) { expiryYear = it }
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Dirección de tarjeta") },
                modifier = Modifier.fillMaxWidth()
            )
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
fun DropdownMenuField(label: String, options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Button(onClick = { expanded = true }) {
            Text(if (selectedOption.isEmpty()) label else selectedOption)
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
fun PreviewPagosScreen() {
    PagosScreen(navController = NavHostController(LocalContext.current))
}
