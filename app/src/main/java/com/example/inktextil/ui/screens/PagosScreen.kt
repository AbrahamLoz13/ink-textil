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
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeContentPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagosScreen(navController: NavHostController) {
    var selectedCard by remember { mutableStateOf<String?>(null) }
    var nameOnCard by remember { mutableStateOf("") }
    var expiryMonth by remember { mutableStateOf("01") }
    var expiryYear by remember { mutableStateOf("2025") }
    var address by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .navigationBarsPadding()
            ) {
                NavBar(navController)
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0) // evitamos dobles paddings
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp)
                .imePadding(), // se adapta al teclado
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("TARJETAS Y CUENTAS", fontSize = 20.sp)
            }

            items(3) { index ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(
                            if (selectedCard == "Card $index") Color(0xFFD1C4E9)
                            else Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable { selectedCard = "Card $index" }
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tarjeta **** **** **** ${1000 + index}")
                }
            }

            item {
                TextField(
                    value = nameOnCard,
                    onValueChange = { nameOnCard = it },
                    label = { Text("Nombre en la tarjeta") },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF6200EE),
                        unfocusedIndicatorColor = Color(0xFFBDBDBD)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomDropdownField(
                        label = "Mes",
                        options = (1..12).map { it.toString().padStart(2, '0') },
                        selectedOption = expiryMonth,
                        onOptionSelected = { expiryMonth = it },
                        modifier = Modifier.weight(1f)
                    )
                    CustomDropdownField(
                        label = "Año",
                        options = (2025..2035).map { it.toString() },
                        selectedOption = expiryYear,
                        onOptionSelected = { expiryYear = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección de tarjeta") },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color(0xFF6200EE),
                        unfocusedIndicatorColor = Color(0xFFBDBDBD)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { /* Guardar lógica */ },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Guardar")
                    }
                    Button(
                        onClick = { /* Eliminar lógica */ },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Eliminar")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        TextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color(0xFF6200EE),
                unfocusedIndicatorColor = Color(0xFFBDBDBD)
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPagosScreen() {
    PagosScreen(navController = NavHostController(LocalContext.current))
}
