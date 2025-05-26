package com.example.inktextil.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.model.ShirtItem
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import com.example.inktextil.ui.model.CarritoViewModel

@Composable
fun WishListScreen(navController: NavHostController, carritoViewModel: CarritoViewModel) {
    val wishlistItems = carritoViewModel.wishlist

    Scaffold(
        bottomBar = { // ✅ Coloca NavBar fijo abajo
            NavBar(navController)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // TopBar con búsqueda
            TopBar(navController)

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "${wishlistItems.size} artículos en wishlist",
                    fontSize = 14.sp
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f) // Asegura que el contenido ocupe el espacio restante
                ) {
                    items(wishlistItems) { item ->
                        WishlistItemCard(
                            item = item,
                            onRemove = { carritoViewModel.eliminarDeWishlist(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WishlistItemCard(
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

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (!item.description.isNullOrBlank()) {
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Text(
                    text = "Talla: ${item.size} | Color: ${item.color}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$${"%.2f".format(item.price)} MXN",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

            }


            Text(
                    text = "Eliminar",
                    color = Color.Red,
                    modifier = Modifier.clickable { onRemove() }
                )
            }
        }
    }


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun WishListScreenPreview() {
    // Aquí debes definir un ViewModel de ejemplo o un mock para la prueba.
    val navController = rememberNavController()
    val carritoViewModel = CarritoViewModel() // Asegúrate de tener datos mockeados o de prueba.
    WishListScreen(navController = navController, carritoViewModel = carritoViewModel)
}
