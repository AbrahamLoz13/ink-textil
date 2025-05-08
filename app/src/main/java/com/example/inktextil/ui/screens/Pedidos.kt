package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.R
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@Composable
fun DetallesPedidoScreen(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) }
    ) { paddingValues ->
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.screenWidthDp > configuration.screenHeightDp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (!isLandscape) {
                Text(
                    text = "PEDIDOS",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                var searchQuery by remember { mutableStateOf("") }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Buscar pedido") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { /* Acción de búsqueda */ },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Buscar", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            val pedidos = listOf(
                Pedido("PLAYERA PULP FICTION", "Ideal para amantes del cine.", R.drawable.playera1, "Envío a CDMX, pago con tarjeta.", "detallesArticulo"),
                Pedido("SUDADERA STAR WARS", "Diseño galáctico y cómodo.", R.drawable.logos, "Envío a Monterrey, pago con PayPal.", "sudaderasScreen"),
                Pedido("GORRA MARVEL", "Para fanáticos de los cómics.", R.drawable.logogorr, "Envío a Guadalajara, pago con efectivo.", "gorrasScreen"),
                Pedido("CAMISETA NIRVANA", "Estilo clásico rockero.", R.drawable.playera1, "Envío a Puebla, pago con tarjeta.", "detallesArticulo"),
                Pedido("CHAQUETA VINTAGE", "Diseño retro y elegante.", R.drawable.logocha, "Envío a Querétaro, pago con transferencia.", "ChaquetasScreen")
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                pedidos.forEach { pedido ->
                    PedidoItem(navController, pedido, isLandscape)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun PedidoItem(navController: NavHostController, pedido: Pedido, isLandscape: Boolean) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = pedido.titulo,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (isLandscape) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PedidoImagenYDescripcion(pedido)
                }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    PedidoImagenYDescripcion(pedido)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(pedido.ruta) },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    when {
                        pedido.ruta.contains("gorras") -> "Ver gorras"
                        pedido.ruta.contains("sudaderas") -> "Ver sudaderas"
                        pedido.ruta.contains("camisetas") -> "Ver camisetas"
                        pedido.ruta.contains("chaquetas") -> "Ver chaquetas"
                        else -> "Ver playeras"
                    }
                )
            }
        }
    }
}

@Composable
fun PedidoImagenYDescripcion(pedido: Pedido) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = pedido.imagen),
            contentDescription = "Imagen del pedido",
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = pedido.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Justify
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pedido.detalles,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetallesPedidoScreenPreview() {
    DetallesPedidoScreen(navController = rememberNavController())
}

data class Pedido(
    val titulo: String,
    val descripcion: String,
    val imagen: Int,
    val detalles: String,
    val ruta: String
)
