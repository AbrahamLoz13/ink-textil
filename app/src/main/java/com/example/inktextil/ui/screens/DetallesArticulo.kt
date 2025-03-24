package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Article("Camiseta", R.drawable.logo),
        Article("Sudadera", R.drawable.x),
        Article("Pantalón", R.drawable.ic_launcher_background),
        Article("Gorra", R.drawable.img),
        Article("Chaqueta", R.drawable.img)
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
            Text(
                text = "PLAYERA PULP FICTION",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(R.drawable.playera1),
                        contentDescription = "Imagen del pedido",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(100.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Este diseño es ideal para personas amantes del cine.",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
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

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Acción para agregar al carrito */ },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) {
                Text("Agregar al carrito", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Recomendaciones",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth().height(500.dp),
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
            .clickable { navController.navigate("detallesPedido/${article.title}") },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(id = article.image),
                    contentDescription = article.title,
                    modifier = Modifier.align(Alignment.Center).size(120.dp)
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