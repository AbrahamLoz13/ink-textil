package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.R
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@Composable
fun WishListScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        // TopBar
        TopBar(navController = navController)

        // Contenido principal
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
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Buscar") },
                    modifier = Modifier.width(200.dp)
                )
            }



            Spacer(modifier = Modifier.height(16.dp))

            // Lista de productos
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(10) { index ->
                    ProductItem(navController)
                }
            }
        }

        // NavBar
        NavBar(navController = navController)
    }
}

@Composable
fun ProductItem(navController: NavController) {
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
                painter = painterResource(id = R.drawable.playera1),
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .size(120.dp)
                    .clickable { navController.navigate("detallesArticulo") } // Navega al hacer clic en la imagen
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Playera Pulp Fiction",
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
    // Usamos un NavController simulado, ya que no podemos crear uno real en el preview
    val navController = rememberNavController()

    // Llamamos a la función WishListScreen con el NavController
    WishListScreen(navController = navController)
}

