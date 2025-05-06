package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import com.example.inktextil.ui.model.CarritoViewModel

@Composable
fun CarritoScreen(navController: NavHostController, carritoViewModel: CarritoViewModel) {
    val cartItems = carritoViewModel.carrito

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopBar(navController)

        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "${cartItems.size} artículos",
                fontSize = 14.sp
            )

            val totalPrice = cartItems.sumOf {
                it.price.replace("$", "").replace("MXN", "").trim().toDoubleOrNull() ?: 0.0
            }

            Text(
                text = "Total: \$${"%.2f".format(totalPrice)}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onRemove = { carritoViewModel.eliminarDelCarrito(item) }
                    )
                }
            }

            Button(
                onClick = {
                    if (cartItems.isNotEmpty()) {
                        navController.navigate("checkout")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                enabled = cartItems.isNotEmpty()
            ) {
                Text("Proceder al pago")
            }

            NavBar(navController)
        }
    }
}

@Composable
fun CartItemCard(
    item: ShirtItem,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.title,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, fontWeight = FontWeight.Bold)
                Text(item.description)
                Text("Talla: ${item.size} | Color: ${item.color}")
                Text(item.price, fontWeight = FontWeight.Bold)

                Text(
                    text = "Eliminar",
                    color = Color.Red,
                    modifier = Modifier.clickable { onRemove() }
                )
            }
        }
    }
}
