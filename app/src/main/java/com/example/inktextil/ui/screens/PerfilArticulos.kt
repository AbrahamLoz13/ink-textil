package com.example.inktextil.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun InicioArticulo(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF0D47A1), Color(0xFF1976D2))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Menú Principal",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            val buttonModifier = Modifier
                .fillMaxWidth()
                .height(60.dp)

            val buttonColors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF0D47A1)
            )

            val options = listOf("Datos", "Pedidos", "Pagos", "Mis diseños", "Wish list", "Historial")

            options.forEach { text ->
                Button(
                    onClick = {},
                    modifier = buttonModifier,
                    shape = RoundedCornerShape(12.dp),
                    colors = buttonColors
                ) {
                    Text(text, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewInicioArticulo() {
    InicioArticulo(navController = rememberNavController())
}

