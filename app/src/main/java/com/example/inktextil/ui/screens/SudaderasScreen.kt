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
import com.example.inktextil.ui.components.HoodieCard
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import com.example.inktextil.ui.model.CarritoViewModel

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
fun CatalogoSudaderas(
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
                text = "Sudaderas",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            hoodieCatalog.forEach { hoodie ->
                HoodieCard(
                    hoodie = hoodie,
                    carritoViewModel = carritoViewModel,
                    snackbarHostState = snackbarHostState
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
