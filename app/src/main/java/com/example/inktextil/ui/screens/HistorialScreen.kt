package com.example.inktextil.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inktextil.R
import com.example.inktextil.model.UsuarioViewModel
import com.example.inktextil.model.Pedido
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@Composable
fun HistorialScreen(navController: NavHostController, viewModel: UsuarioViewModel = viewModel()) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    val pedidos by remember { derivedStateOf { viewModel.historialPedidos } }

    // Cargar historial una sola vez
    LaunchedEffect(Unit) {
        viewModel.cargarHistorialPedidos()
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
            }

            if (pedidos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay pedidos aún", color = Color.Gray, fontSize = 16.sp)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(pedidos) { pedido ->
                        PedidosItem(pedido)
                    }
                }
            }

            NavBar(navController)
        }
    }
}
@Composable
fun PedidosItem(pedido: Pedido) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Pedido ID: ${pedido.id.take(8)}", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))

            pedido.productos.firstOrNull()?.let { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(70.dp)
                            .background(Color.LightGray, shape = RoundedCornerShape(6.dp))
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(item.title, fontWeight = FontWeight.SemiBold, fontSize = 15.sp)
                        Text("Talla: ${item.size}, Color: ${item.color}", fontSize = 13.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("Total: $${"%.2f".format(pedido.total)} MXN", color = Color(0xFF1A237E))
        }
    }
}
