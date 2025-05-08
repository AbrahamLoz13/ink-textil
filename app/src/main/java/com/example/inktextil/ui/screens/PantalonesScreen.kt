package com.example.inktextil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.R
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.PantsCard
import com.example.inktextil.ui.components.TopBar
import com.example.inktextil.ui.model.CarritoViewModel

// Modelo de pantalón
data class PantsItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val size: String,
    val color: String,
    val price: String
)

val pantsCatalog = listOf(
    PantsItem("Pantalón Cargo", "Con bolsillos laterales y tela resistente.", R.drawable.logopan, "M, L, XL", "Negro", "$350 MXN"),
    PantsItem("Jeans Clásicos", "Corte recto y cómodo para uso diario.", R.drawable.logopan, "S, M, L", "Azul", "$400 MXN"),
    PantsItem("Joggers Deportivos", "Para entrenamiento o descanso.", R.drawable.logopan, "M, L", "Gris", "$320 MXN")
)

@Composable
fun CatalogoPantalones(
    navController: NavHostController,
    carritoViewModel: CarritoViewModel
) {
    carritoViewModel.obtenerCarrito()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) },
        containerColor = Color(0xFFF5F5F5),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Pantalones",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            pantsCatalog.forEach { pants ->
                PantsCard(
                    pants = pants,
                    carritoViewModel = carritoViewModel,
                    snackbarHostState = snackbarHostState
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
