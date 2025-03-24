package com.example.inktextil.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle

// Definir colores personalizados
val DarkBlue = Color(0xFF003366) // Azul oscuro
val LightBlue = Color(0xFF1976D2) // Azul claro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue), // Fondo azul oscuro
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically // Alineación vertical centrada
        ) {
            Icon(
                imageVector = Icons.Filled.AccountCircle,
                contentDescription = "Perfil",
                tint = Color.Black, // Color del ícono
                modifier = Modifier
                    .size(32.dp) // Tamaño del ícono
                    .height(56.dp) // Altura igual al TextField
            )
            Spacer(modifier = Modifier.width(8.dp)) // Espacio entre el ícono y el campo de texto
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar", color = Color.Black) }, // Placeholder negro
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black, // Texto de entrada negro
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = Color.White, // Fondo del campo de texto blanco
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = LightBlue, // Borde azul claro cuando está enfocado
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    cursorColor = Color.Black
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(navController = rememberNavController())
}
