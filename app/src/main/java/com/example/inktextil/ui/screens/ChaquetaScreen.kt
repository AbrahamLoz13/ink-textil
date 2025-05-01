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

data class ChaquetaItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val size: String,
    val color: String,
    val price: String
)

val chaquetaCatalog = listOf(
    ChaquetaItem("Chaqueta de mezclilla", "Perfecta para clima fresco.", R.drawable.logocha, "M, L", "Azul", "$400 MXN"),
    ChaquetaItem("Chaqueta bomber", "Estilo urbano y moderno.", R.drawable.logocha, "L, XL", "Negro", "$450 MXN"),
    ChaquetaItem("Chaqueta impermeable", "Ideal para días lluviosos.", R.drawable.logocha, "S, M, L", "Verde", "$480 MXN"),
    ChaquetaItem("Chaqueta polar", "Mantente abrigado todo el invierno.", R.drawable.logocha, "M, L", "Gris", "$500 MXN")
)

@Composable
fun ChaquetasScreen(navController: NavHostController) {
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
                text = "Chaquetas",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF212121),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            chaquetaCatalog.forEach { item ->
                ChaquetaCard(item)
                Spacer(modifier = Modifier.height(20.dp))
            }
            Spacer(modifier = Modifier.height(70.dp))
        }
    }
}

@Composable
fun ChaquetaCard(chaqueta: ChaquetaItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = chaqueta.imageRes),
                contentDescription = chaqueta.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(chaqueta.title, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color(0xFF0D47A1))
                Spacer(modifier = Modifier.height(4.dp))
                Text(chaqueta.description, fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Talla: ${chaqueta.size}", fontSize = 13.sp, color = Color.DarkGray)
                Text("Color: ${chaqueta.color}", fontSize = 13.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(chaqueta.price, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD32F2F))
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
fun PreviewCatalogoChaquetas() {
    val navController = rememberNavController()
    ChaquetasScreen(navController)
}
