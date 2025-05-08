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
import com.example.inktextil.ui.model.CarritoViewModel
import com.example.inktextil.ui.screens.CapItem
import com.example.inktextil.ui.screens.ShirtItem
import kotlinx.coroutines.launch

@Composable
fun CapCard(
    cap: CapItem,
    carritoViewModel: CarritoViewModel,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()

    val shirtItem = ShirtItem(
        title = cap.title,
        description = cap.description,
        imageRes = cap.imageRes,
        size = cap.size,
        color = cap.color,
        price = cap.price
    )

    val isWishlisted = carritoViewModel.wishlist.contains(shirtItem)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = cap.imageRes),
                contentDescription = cap.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(cap.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(cap.description)
            Text("Talla: ${cap.size} | Color: ${cap.color}")
            Text(cap.price, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    carritoViewModel.agregarAlCarrito(shirtItem)
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Agregado al carrito: ${cap.title}")
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
                            if (isWishlisted) "Eliminado de la wishlist: ${cap.title}"
                            else "Agregado a la wishlist: ${cap.title}"
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
