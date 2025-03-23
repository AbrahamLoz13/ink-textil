package com.example.inktextil.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun CarritoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            "CARRITO",
            style = MaterialTheme.typography.titleMedium,
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar pedido") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {},
                shape = RoundedCornerShape(4.dp)
            ) {
                Text("Filtrar")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items((1..4).toList()) { _ ->
                CarritoItem()
            }
        }
    }
}

@Composable
fun CarritoItem() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "Articulo",
            modifier = Modifier.weight(1f),
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .size(80.dp) //
                .border(1.dp, Color.LightGray, shape = RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(android.R.drawable.ic_menu_gallery),
                contentDescription = "Imagen",
                tint = Color.Gray,
                modifier = Modifier.size(50.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarritoScreenPreview() {
    MaterialTheme {
        CarritoScreen()
    }
}
