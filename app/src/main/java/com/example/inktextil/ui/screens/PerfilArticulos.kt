package com.example.inktextil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun InicioArticulo() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
    ) {
        val buttonModifier = Modifier
            .fillMaxWidth(0.8f)
            .height(60.dp)

        Button(
            onClick = {},
            modifier = buttonModifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Datos", fontSize = 16.sp)
        }
        Button(
            onClick = {},
            modifier = buttonModifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Pedidos", fontSize = 16.sp)
        }
        Button(
            onClick = {},
            modifier = buttonModifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Pagos", fontSize = 16.sp)
        }
        Button(
            onClick = {},
            modifier = buttonModifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Mis diseños", fontSize = 16.sp)
        }
        Button(
            onClick = {},
            modifier = buttonModifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Wish list", fontSize = 16.sp)
        }
        Button(
            onClick = {},
            modifier = buttonModifier,
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Historial", fontSize = 16.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicioArticuloPreview() {
    MaterialTheme {
        InicioArticulo()
    }
}
