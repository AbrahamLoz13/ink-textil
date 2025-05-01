package com.example.inktextil.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
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

@Composable
fun HistorialScreen(navController: NavHostController) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(navController)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            if (isPortrait) {
                Text(
                    text = "HISTORIAL",
                    color = colorResource(R.color.Blue900),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Buscar pedido") },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {},
                        modifier = Modifier.height(50.dp)
                    ) {
                        Text("Buscar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items((1..8).toList()) { index ->
                    PedidosItem(index)
                }
            }

            NavBar(navController)
        }
    }
}

@Composable
fun PedidosItem(index: Int) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Playera Pulp Fiction", fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                Text("Pedido #$index", fontSize = 14.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.drawable.playera1),
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .size(80.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(4.dp))
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHistorialScreen() {
    val navController = rememberNavController()
    HistorialScreen(navController)
}
