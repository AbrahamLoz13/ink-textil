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

import kotlinx.coroutines.launch

@Composable
fun ShirtCard(
    shirt: com.example.inktextil.ui.screens.ShirtItem,
    carritoViewModel: CarritoViewModel,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()

    val shirtItem = ShirtItem(
        title = shirt.title,
        description = shirt.description,
        size = shirt.size,
        color = shirt.color,
        price = shirt.price,
        imageRes = shirt.imageRes
    )
    val isWishlisted = carritoViewModel.wishlist.contains(shirtItem)



    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = shirt.imageRes),
                contentDescription = shirt.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(shirt.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(shirt.description)
            Text("Talla: ${shirt.size} | Color: ${shirt.color}")
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "$${
                    "%.2f".format(
                        shirt.price
                            .replace("$", "")
                            .replace("MXN", "")
                            .trim()
                            .toDoubleOrNull() ?: 0.0
                    )
                } MXN",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    carritoViewModel.agregarAlCarrito(shirtItem)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Agregado al carrito: ${shirt.title}")
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
                            if (isWishlisted) "Eliminado de la wishlist: ${shirt.title}"
                            else "Agregado a la wishlist: ${shirt.title}"
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
