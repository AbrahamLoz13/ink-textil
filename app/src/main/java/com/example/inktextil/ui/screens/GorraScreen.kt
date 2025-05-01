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

data class GorraItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val size: String,
    val color: String,
    val price: String
)

val gorrasCatalog = listOf(
    GorraItem("Gorra NY", "Estilo clásico New York Yankees.", R.drawable.gorralog, "Única", "Negro", "$190 MXN"),
    GorraItem("Gorra Vans", "Diseño moderno y urbano.", R.drawable.gorralog, "Única", "Gris", "$210 MXN"),
    GorraItem("Gorra NASA", "Para los fanáticos del espacio.", R.drawable.gorralog, "Única", "Blanca", "$230 MXN"),
    GorraItem("Gorra Star Wars", "Diseño de la saga galáctica.", R.drawable.gorralog, "Única", "Negro", "$200 MXN")
)

@Composable
fun GorrasScreen(navController: NavHostController) {
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
                text = "Gorras",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF212121),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            gorrasCatalog.forEach { gorra ->
                GorraCard(gorra)
                Spacer(modifier = Modifier.height(20.dp))
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
fun GorraCard(gorra: GorraItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = gorra.imageRes),
                contentDescription = gorra.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(gorra.title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color(0xFF0D47A1))
                Spacer(modifier = Modifier.height(4.dp))
                Text(gorra.description, fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Tamaño: ${gorra.size}", fontSize = 13.sp, color = Color.DarkGray)
                Text("Color: ${gorra.color}", fontSize = 13.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(gorra.price, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD32F2F))
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

@Preview(showBackground = true)
@Composable
fun PreviewCatalogoGorras() {
    val navController = rememberNavController()
    GorrasScreen(navController)
}
