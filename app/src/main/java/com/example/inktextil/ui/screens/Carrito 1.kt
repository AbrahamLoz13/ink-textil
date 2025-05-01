package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@Composable
fun CarritoScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }
    var cartItems by remember {
        mutableStateOf(listOf(
            CartItem(1, "Camiseta Premium algodón", 24.99, 19.99, 1, "https://example.com/shirt.jpg"),
            CartItem(2, "Pantalón vaquero ajustado", 49.99, 39.99, 1, "https://example.com/jeans.jpg"),
            CartItem(3, "Zapatillas deportivas", 89.99, 0.0, 1, "https://example.com/shoes.jpg"),
            CartItem(4, "Reloj inteligente", 199.99, 159.99, 1, "https://example.com/watch.jpg")
        ))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

    ) {
        TopBar(navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Barra de estado del carrito
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${cartItems.size} artículos",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Precio total: \$${"%.2f".format(cartItems.sumOf { it.currentPrice() * it.quantity })}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

            }

            // Lista de artículos en el carrito
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(cartItems) { item ->
                    CartItemCard(
                        item = item,
                        onRemove = {
                            cartItems = cartItems.filterNot { it.id == item.id }
                        },
                        onQuantityChange = { newQuantity ->
                            cartItems = cartItems.map {
                                if (it.id == item.id) it.copy(quantity = newQuantity) else it
                            }
                        }
                    )
                }
            }

            // Resumen de compra
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Resumen del pedido",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal (${cartItems.size} artículos):", fontSize = 14.sp)
                        Text("\$${"%.2f".format(cartItems.sumOf { it.currentPrice() * it.quantity })}",
                            fontSize = 14.sp)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Envío:", fontSize = 14.sp)
                        Text("Gratis", fontSize = 14.sp, color = Color(0xFF007600))
                    }

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text("\$${"%.2f".format(cartItems.sumOf { it.currentPrice() * it.quantity })}",
                            fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }

                    // Botón de compra
                    Button(
                        onClick = { navController.navigate("checkout") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Proceder al pago", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }


                    // Texto de seguridad
                    Text(
                        text = "El precio y la disponibilidad de los productos están sujetos a cambio.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            NavBar(
                navController
            )
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onRemove: () -> Unit,
    onQuantityChange: (Int) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                // Imagen del producto
                Image(
                    painter = rememberAsyncImagePainter(
                        model = item.imageUrl,
                        placeholder = painterResource(android.R.drawable.ic_menu_gallery)
                    ),
                    contentDescription = "Imagen del producto",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(12.dp))

                // Detalles del producto
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = item.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Precio con descuento
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "\$${"%.2f".format(item.currentPrice())}",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFB12704)
                        )

                        // Mostrar precio original tachado si hay descuento
                        if (item.originalPrice > item.discountPrice && item.discountPrice > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "\$${"%.2f".format(item.originalPrice)}",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                textDecoration = TextDecoration.LineThrough
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Selector de cantidad
                    QuantitySelector(
                        currentQuantity = item.quantity,
                        onQuantityChange = onQuantityChange
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Opciones
                    Row {
                        Text(
                            text = "Eliminar",
                            color = Color(0xFF0066C0),
                            modifier = Modifier.clickable { onRemove() }
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = "Guardar para más tarde",
                            color = Color(0xFF0066C0),
                            modifier = Modifier.clickable { /* Acción */ }
                        )
                    }
                }
            }

            // Mensaje de envío gratis
            if (item.currentPrice() > 25) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "✔ Envío GRATIS",
                    color = Color(0xFF007600),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun QuantitySelector(currentQuantity: Int, onQuantityChange: (Int) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(1.dp, Color.LightGray, RoundedCornerShape(4.dp))
            .height(32.dp)
    ) {
        // Botón disminuir cantidad
        IconButton(
            onClick = { if (currentQuantity > 1) onQuantityChange(currentQuantity - 1) },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Disminuir cantidad",
                tint = if (currentQuantity > 1) Color.DarkGray else Color.LightGray
            )
        }

        // Cantidad actual
        Text(
            text = currentQuantity.toString(),
            modifier = Modifier.padding(horizontal = 8.dp),
            fontSize = 14.sp
        )

        // Botón aumentar cantidad
        IconButton(
            onClick = { onQuantityChange(currentQuantity + 1) },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowUp,
                contentDescription = "Aumentar cantidad",
                tint = Color.DarkGray
            )
        }
    }
}

data class CartItem(
    val id: Int,
    val name: String,
    val originalPrice: Double,
    val discountPrice: Double,
    var quantity: Int,
    val imageUrl: String
) {
    fun currentPrice(): Double {
        return if (discountPrice > 0.0) discountPrice else originalPrice
    }
}

@Preview(showBackground = true)
@Composable
fun CarritoScreenPreview() {
    CarritoScreen(navController = rememberNavController())
}