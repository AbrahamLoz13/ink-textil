package com.example.inktextil.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inktextil.ui.screens.CarritoViewModel
import com.example.inktextil.ui.screens.HoodieItem
import com.example.inktextil.ui.screens.ShirtItem
import kotlinx.coroutines.launch

@Composable
fun HoodieCard(
    hoodie: HoodieItem,
    carritoViewModel: CarritoViewModel,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()

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
            Text(hoodie.price, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    carritoViewModel.agregarAlCarrito(
                        ShirtItem(
                            title = hoodie.title,
                            description = hoodie.description,
                            imageRes = hoodie.imageRes,
                            size = hoodie.size,
                            color = hoodie.color,
                            price = hoodie.price
                        )
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Agregado al carrito: ${hoodie.title}")
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Agregar al carrito")
            }
        }
    }
}
