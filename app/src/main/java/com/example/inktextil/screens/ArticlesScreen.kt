package com.example.inktextil.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.R
import com.example.inktextil.components.NavBar
import com.example.inktextil.components.TopBar

data class Article(val title: String, val image: Int)

@Composable
fun ArticlesScreen(navController: NavController) {
    val articles = listOf(
        Article("Camiseta", R.drawable.logo),
        Article("Sudadera", R.drawable.twitter_icon),
        Article("Pantalón", R.drawable.ic_launcher_background),
        Article("Gorra", R.drawable.facebook_icon),
        Article("Chaqueta", R.drawable.google_icon)
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
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Mantiene 2 columnas
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(articles) { article ->
                    ArticleCard(article) {
                        navController.navigate("ArticleDetail/${article.title}")
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp) // Tarjeta más grande pero con el mismo diseño
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = article.image),
                contentDescription = article.title,
                modifier = Modifier
                    .height(120.dp) // Imagen más grande pero con la misma proporción
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = article.title,
                fontSize = 16.sp, // Manteniendo el tamaño del texto
                modifier = Modifier.padding(8.dp),
                color = colors.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesScreenPreview() {
    val navController = rememberNavController()
    ArticlesScreen(navController)
}
