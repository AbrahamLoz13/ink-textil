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
import com.example.inktextil.ui.model.CarritoViewModel
import com.example.inktextil.ui.screens.PantsItem
import kotlinx.coroutines.launch

@Composable
fun PantsCard(
    pants: PantsItem,
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
                painter = painterResource(id = pants.imageRes),
                contentDescription = pants.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(pants.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(pants.description)
            Text("Talla: ${pants.size} | Color: ${pants.color}")
            Text(pants.price, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    carritoViewModel.agregarAlCarrito(
                        com.example.inktextil.ui.screens.ShirtItem(
                            title = pants.title,
                            description = pants.description,
                            imageRes = pants.imageRes,
                            size = pants.size,
                            color = pants.color,
                            price = pants.price
                        )
                    )
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Agregado al carrito: ${pants.title}")
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Agregar al carrito")
            }
        }
    }
}
