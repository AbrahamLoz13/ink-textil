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
import com.example.inktextil.ui.components.ShirtCard
import com.example.inktextil.ui.components.TopBar

data class ShirtItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val size: String,
    val color: String,
    val price: String
)

val shirtCatalog = listOf(
    ShirtItem("Playera Pulp Fiction", "Diseño ideal para cinéfilos.", R.drawable.playera1, "M, L, XL", "Negro", "$250 MXN"),
    ShirtItem("Playera Breaking Bad", "Diseño de la serie Breaking Bad.", R.drawable.playera1, "M, L", "Gris", "$270 MXN"),
    ShirtItem("Playera Naruto", "Ideal para fans del anime Naruto.", R.drawable.playera1, "S, M, L", "Naranja", "$230 MXN"),
    ShirtItem("Playera Star Wars", "Diseño de Darth Vader.", R.drawable.playera1, "M, L, XL", "Negro", "$260 MXN"),
    ShirtItem("Playera Marvel", "Diseño de los Vengadores.", R.drawable.playera1, "S, M", "Azul", "$240 MXN"),
    ShirtItem("Playera AC/DC", "Estilo rockero clásico.", R.drawable.playera1, "M, L, XL", "Negro", "$255 MXN")
)
@Composable
fun CatalogoPlayeras(
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
                text = "Catálogo",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            shirtCatalog.forEach { shirt ->
                ShirtCard(
                    shirt = shirt,
                    carritoViewModel = carritoViewModel,
                    snackbarHostState = snackbarHostState
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}