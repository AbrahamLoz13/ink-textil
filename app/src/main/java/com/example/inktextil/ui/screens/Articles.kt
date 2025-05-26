package com.example.inktextil.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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

data class Article(val title: String, val image: Int, val description: String, val route: String)

@Composable
fun ArticlesScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val featuredProducts = listOf(
        Article("Oferta Camiseta", R.drawable.logopa, "Camiseta en descuento por tiempo limitado.", "detallesArticulo"),
        Article("Nueva Sudadera", R.drawable.sudaderaong, "Sudadera edición limitada recién llegada.", "sudaderasScreen"),
        Article("Playera Estampada", R.drawable.logopa, "Diseño exclusivo con estampado moderno.", "detallesArticulo"),
        Article("Conjunto Deportivo", R.drawable.logopan, "Conjunto cómodo y resistente para entrenar.", "pantalonesScreen"),
        Article("Gorra Clásica", R.drawable.gorralog, "Estilo retro con bordado personalizado.", "gorrasScreen")
    )

    val articles = listOf(
        Article("Camisetas", R.drawable.logopa, "Explora nuestra colección de camisetas de alta calidad.", "detallesArticulo"),
        Article("Sudaderas", R.drawable.sudaderaong, "Descubre sudaderas cómodas y con diseños únicos.", "sudaderasScreen"),
        Article("Pantalones", R.drawable.logopan, "Variedad de pantalones para cualquier ocasión.", "pantalonesScreen"),
        Article("Gorras", R.drawable.gorralog, "Elige entre múltiples estilos de gorras ajustables.", "gorrasScreen"),
        Article("Chaquetas", R.drawable.logocha, "Chaquetas ligeras y modernas para cualquier temporada.", "chaquetasScreen")
    )

    val bottomSuggestions = listOf(
        Article("Accesorios Nuevos", R.drawable.gorralog, "Explora los accesorios más recientes.", "gorrasScreen"),
        Article("Chaqueta Impermeable", R.drawable.logocha, "Perfecta para días lluviosos.", "chaquetasScreen"),
        Article("Pantalón Jogger", R.drawable.logopan, "Ideal para outfits casuales y cómodos.", "pantalonesScreen"),
        Article("Playera Básica", R.drawable.logopa, "Un básico que no puede faltar.", "detallesArticulo"),
        Article("Sudadera Oversize", R.drawable.sudaderaong, "Tendencia actual para todos los estilos.", "sudaderasScreen")
    )

    if(isPortrait){
        Scaffold(
            topBar = {TopBar(navController)},
            bottomBar = {NavBar(navController)}
        ){ paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ){
                Text(
                    text = "Artículos Disponibles",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        SectionTitle("Destacados")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(featuredProducts) { article ->
                                MiniArticleCard(article) { navController.navigate(article.route) }
                            }
                        }
                    }

                    items(articles) { article ->
                        ArticleCard(article) { navController.navigate(article.route) }
                    }

                    item {
                        SectionTitle("Sugerencias")
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(bottomSuggestions) { article ->
                                MiniArticleCard(article) { navController.navigate(article.route) }
                            }
                        }
                    }
                }
            }
        }

    } else {
        Scaffold(
            topBar = { TopBar(navController) },
            bottomBar = { NavBar(navController) }

        ) { paddingValues ->

            LazyRow(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalArrangement = Arrangement.spacedBy(16.dp)

            ) {
                items(articles) { article ->
                    Box(modifier = Modifier.width(300.dp)) {
                        ArticleCard(article) { navController.navigate(article.route) }
                    }
                }
            }

    }
        }
    }


@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
    )
}

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = article.image),
                contentDescription = article.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp, fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Ver productos")
                }
            }
        }
    }
}

@Composable
fun MiniArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = article.image),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewArticlesScreen() {
    val navController = rememberNavController()
    ArticlesScreen(navController)
}
