package com.example.inktextil.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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

data class HoodieItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val size: String,
    val color: String,
    val price: String
)

val hoodieCatalog = listOf(
    HoodieItem("Sudadera Stranger Things", "Diseño con logo del Upside Down.", R.drawable.sudaderaong, "M, L, XL", "Rojo", "$480 MXN"),
    HoodieItem("Sudadera Rick & Morty", "Diseño con personajes icónicos.", R.drawable.sudaderaong, "S, M, L", "Verde", "$450 MXN"),
    HoodieItem("Sudadera NASA", "Estilo futurista y cómodo.", R.drawable.sudaderaong, "M, L", "Blanco", "$500 MXN"),
    HoodieItem("Sudadera IT", "Perfecta para los fans del terror.", R.drawable.sudaderaong, "L, XL", "Negro", "$470 MXN"),
    HoodieItem("Sudadera Marvel", "Diseño de los Vengadores.", R.drawable.sudaderaong, "S, M", "Azul marino", "$460 MXN"),
    HoodieItem("Sudadera Looney Tunes", "Diseño clásico animado.", R.drawable.sudaderaong, "M, L, XL", "Gris", "$440 MXN")
)

@Composable
fun SudaderasScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Sudaderas",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF212121),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            hoodieCatalog.forEach { hoodie ->
                HoodieCard(hoodie)
                Spacer(modifier = Modifier.height(20.dp))
            }

            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
fun HoodieCard(hoodie: HoodieItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = hoodie.imageRes),
                contentDescription = hoodie.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = hoodie.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = Color(0xFF0D47A1)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = hoodie.description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Talla: ", fontSize = 13.sp, color = Color.DarkGray)
                    Text(hoodie.size, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Color: ", fontSize = 13.sp, color = Color.DarkGray)
                    Text(hoodie.color, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = hoodie.price,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFD32F2F)
                    )

                    OutlinedButton(
                        onClick = { /* Agregar */ },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF0D47A1)),
                        border = BorderStroke(1.dp, Color(0xFF0D47A1))
                    ) {
                        Text("Agregar", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewCatalogoSudaderas() {
    val navController = rememberNavController()
    SudaderasScreen(navController)
}
