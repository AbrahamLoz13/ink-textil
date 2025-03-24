package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.R
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

// Data class para representar un artículo
data class Article(val title: String, val image: Int)

@Composable
fun DetallesArticulo(navController: NavHostController) {
    val recommendedArticles = listOf(
        Article("Camiseta", R.drawable.logopa),
        Article("Sudadera", R.drawable.logos),
        Article("Pantalón", R.drawable.logopan),
        Article("Gorra", R.drawable.logogorr),
        Article("Chaqueta", R.drawable.logocha)
    )

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Título principal
            Text(
                text = "PLAYERA PULP FICTION",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1), // Azul oscuro
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Imagen y detalles del artículo
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(12.dp))
                        .shadow(8.dp, shape = RoundedCornerShape(12.dp)) // Agrega sombra
                ) {
                    Image(
                        painter = painterResource(R.drawable.playera1),
                        contentDescription = "Imagen del pedido",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(110.dp)
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                // Descripción del producto
                Column {
                    Text(
                        text = "Este diseño es ideal para personas amantes del cine.",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Talla: M, L, XL", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text(text = "Color: Negro", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text(text = "Impresión: Serigrafía de alta calidad", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text(text = "Material: Algodón 100%", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text(text = "Ajuste: Regular Fit", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                    Text(text = "Lavado: Lavar a máquina en frío, no usar blanqueador", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Botón de agregar al carrito
            Button(
                onClick = { /* Acción para agregar al carrito */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)) // Azul fuerte
            ) {
                Text("Agregar al carrito", fontSize = 18.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Título para las recomendaciones
            Text(
                text = "Recomendaciones",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Grid de artículos recomendados
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(recommendedArticles) { article ->
                    RecommendedArticleCard(article, navController)
                }
            }
        }
    }
}

@Composable
fun RecommendedArticleCard(article: Article, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .clickable { navController.navigate("detallepedidos") },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp) // Elevación añadida
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                    .shadow(4.dp, shape = RoundedCornerShape(12.dp)) // Sombra
            ) {
                Image(
                    painter = painterResource(id = article.image),
                    contentDescription = article.title,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(120.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = article.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetallesPedidoScreen() {
    val navController = rememberNavController()
    DetallesArticulo(navController)
}
