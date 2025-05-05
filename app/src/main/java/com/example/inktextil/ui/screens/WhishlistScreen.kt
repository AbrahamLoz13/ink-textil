package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.R
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

data class WishItem(val title: String, val imageRes: Int)

@Composable
fun WishListScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    // Lista completa de productos
    val allProducts = listOf(
        WishItem("Playera Pulp Fiction", R.drawable.playera1),
        WishItem("Sudadera Breaking Bad", R.drawable.sudaderaong),
        WishItem("Pantalón Stranger Things", R.drawable.logopan),
        WishItem("Gorra Rick and Morty", R.drawable.gorralog),
        WishItem("Chaqueta Naruto", R.drawable.logocha)
    )

    // Lista filtrada según la búsqueda
    val filteredProducts = allProducts.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // TopBar con búsqueda real
        TopBar(
            navController = navController,
            onSearch = { searchQuery = it }
        )

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "WISH LIST",
                    color = colorResource(R.color.Blue900),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 27.sp,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(filteredProducts) { product ->
                    ProductItem(navController = navController, title = product.title, imageRes = product.imageRes)
                }
            }
        }

        NavBar(navController = navController)
    }
}

@Composable
fun ProductItem(navController: NavHostController, title: String, imageRes: Int) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier
                    .size(120.dp)
                    .clickable { navController.navigate("detallesArticulo") }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Acción para agregar al carrito */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar al carrito")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWishListScreen() {
    val navController = rememberNavController()
    WishListScreen(navController = navController)
}
