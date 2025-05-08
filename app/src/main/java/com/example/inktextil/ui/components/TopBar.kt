package com.example.inktextil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.navigation.NavController
import androidx.navigation.NavHostController

val DarkBlue = Color(0xFF003366)
val LightBlue = Color(0xFF1976D2)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController,
    modifier: Modifier = Modifier,
    placeholderText: String = "Buscar",
    onSearch: (String) -> Unit = {} // Acción opcional al escribir
) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .statusBarsPadding()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                onSearch(it) // Llama a la función cuando cambia el texto
            },
            placeholder = { Text(placeholderText, color = Color.Black) },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = LightBlue,
                unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .width(280.dp)
                .height(56.dp)
        )
    }
}
