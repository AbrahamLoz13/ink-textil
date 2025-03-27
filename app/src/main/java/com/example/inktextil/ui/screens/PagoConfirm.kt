package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

@Composable
fun PaymentSuccessScreen(navController: NavHostController) {
    val recommendedArticles = listOf(
        Article("Camiseta", R.drawable.logopa),
        Article("Sudadera", R.drawable.logos),
        Article("Pantalón", R.drawable.logopan),
        Article("Gorra", R.drawable.logogorr),
        Article("Chaqueta", R.drawable.logocha)
    )

    Scaffold(
        topBar = { TopBar(navController, "Finalizar compra") },
        bottomBar = { NavBar(
            navController, Modifier

        ) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pago Exitoso",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Resumen del Artículo",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(R.drawable.playera1),
                        contentDescription = "Imagen del artículo",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(110.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(text = "PLAYERA PULP FICTION", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Talla: M, L, XL", fontSize = 14.sp)
                    Text(text = "Color: Negro", fontSize = 14.sp)
                    Text(text = "Precio: $350 MXN", fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Recomendaciones",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
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

@Preview(showBackground = true)
@Composable
fun PreviewPaymentSuccessScreen() {
    val navController = rememberNavController()
    PaymentSuccessScreen(navController)
}