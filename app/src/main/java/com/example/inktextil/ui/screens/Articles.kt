import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.R
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

data class Article(val title: String, val image: Int, val price: Double)

@Composable
fun ArticlesScreen(navController: NavHostController) {
    val categories = listOf("Tendencias", "Más Barato", "Ofertas Relámpago", "Mujer", "Hombre", "Niño")
    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val articles = listOf(
        Article("Camiseta", R.drawable.logopa, 19.99),
        Article("Sudadera", R.drawable.logos, 34.99),
        Article("Pantalón", R.drawable.logopan, 29.99),
        Article("Gorra", R.drawable.logogorr, 14.99),
        Article("Chaqueta", R.drawable.logocha, 49.99)
    )

    val sortedArticles = remember(selectedCategory) {
        when (selectedCategory) {
            "Más Barato" -> articles.sortedBy { it.price }
            "Ofertas Relámpago" -> articles.shuffled()
            else -> articles
        }
    }

    val dailyOffers = remember { articles.shuffled().take(3) }

    Scaffold(
        topBar = { TopBar(navController, "Finalizar compra") },
        bottomBar = { NavBar(
            navController, modifier = Modifier

        ) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp)
        ) {
            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(categories.size) { index ->
                        val category = categories[index]
                        Button(
                            onClick = { selectedCategory = category },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = category, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            items(sortedArticles) { article ->
                ArticleCard(article) {
                    navController.navigate("detallesArticulo")
                }
            }

            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Ofertas del Día 🔥",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )
            }

            item {
                LazyRow(
                    modifier = Modifier.padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(dailyOffers) { article ->
                        SmallArticleCard(article) {
                            navController.navigate("detallesArticulo")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .clickable { onClick() }
            .padding(6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = article.image),
                contentDescription = article.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .padding(8.dp)
            )
            Text(text = article.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "$${article.price}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun SmallArticleCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(160.dp)
            .clickable { onClick() }
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = article.image),
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .padding(4.dp)
            )
            Text(text = article.title, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Text(text = "$${article.price}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
    }
}
