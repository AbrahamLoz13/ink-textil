package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "MIS PEDIDOS",
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val pedidos = listOf(
                    Pedido("PLAYERA PULP FICTION", "Ideal para amantes del cine.", R.drawable.playera1, "Envío a CDMX, pago con tarjeta."),
                    Pedido("SUDADERA STAR WARS", "Diseño galáctico y cómodo.", R.drawable.playera1, "Envío a Monterrey, pago con PayPal."),
                    Pedido("GORRA MARVEL", "Para fanáticos de los cómics.", R.drawable.playera1, "Envío a Guadalajara, pago con efectivo."),
                    Pedido("CAMISETA NIRVANA", "Estilo clásico rockero.", R.drawable.playera1, "Envío a Puebla, pago con tarjeta."),
                    Pedido("CHAQUETA VINTAGE", "Diseño retro y elegante.", R.drawable.playera1, "Envío a Querétaro, pago con transferencia.")
                )

                pedidos.forEach { pedido ->
                    PedidoItem(navController, pedido)
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun PedidoItem(navController: NavHostController, pedido: Pedido) {
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

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = pedido.imagen),
                    contentDescription = "Imagen del pedido",
                    modifier = Modifier.size(100.dp)
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

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { navController.navigate("detallesArticulo") },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Ver artículo")
                }
                Button(
                    onClick = { /* Acción sin definir */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Comprar nuevamente")
                }
            }
        }
    }
}

data class Pedido(
    val titulo: String,
    val descripcion: String,
    val imagen: Int,
    val detalles: String
)



