package com.example.inktextil.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inktextil.model.ShirtItem
import com.example.inktextil.ui.model.CarritoViewModel
import com.example.inktextil.ui.screens.HoodieItem
import kotlinx.coroutines.launch
@Composable
fun HoodieCard(
    hoodie: HoodieItem,
    carritoViewModel: CarritoViewModel,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()

    // ✅ Convertir HoodieItem a ShirtItem
    val shirtItem = ShirtItem(
        title = hoodie.title,
        description = hoodie.description,
        size = hoodie.size,
        color = hoodie.color,
        price = hoodie.price,
        imageRes = hoodie.imageRes
    )

    val isWishlisted = carritoViewModel.wishlist.contains(shirtItem)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Image(
                painter = painterResource(id = hoodie.imageRes),
                contentDescription = hoodie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(hoodie.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(hoodie.description)
            Text("Talla: ${hoodie.size} | Color: ${hoodie.color}")

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$${"%.2f".format(
                    hoodie.price
                        .replace("$", "")
                        .replace("MXN", "")
                        .trim()
                        .toDoubleOrNull() ?: 0.0
                )} MXN",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    carritoViewModel.agregarAlCarrito(shirtItem)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Agregado al carrito: ${hoodie.title}")
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Agregar al carrito")
            }

            IconButton(
                onClick = {
                    carritoViewModel.toggleWishlist(shirtItem)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            if (isWishlisted)
                                "Eliminado de la wishlist: ${hoodie.title}"
                            else
                                "Agregado a la wishlist: ${hoodie.title}"
                        )
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = if (isWishlisted) Icons.Filled.Star else Icons.Outlined.Star,
                    contentDescription = "Wishlist",
                    tint = if (isWishlisted) Color.Yellow else Color.Gray
                )
            }
        }
    }
}
