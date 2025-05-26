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
import com.example.inktextil.ui.components.JacketCard
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import com.example.inktextil.ui.model.CarritoViewModel


// Modelo de chaqueta
data class JacketItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val size: String,
    val color: String,
    val price: String
)
val jacketCatalog = listOf(
    JacketItem("Chaqueta Invierno", "Chaqueta térmica para el frío.", R.drawable.logocha, "M, L, XL", "Negro", "$750 MXN"),
    JacketItem("Chaqueta Deportiva", "Ideal para ejercicio y clima fresco.", R.drawable.logocha, "S, M, L", "Gris", "$680 MXN"),
    JacketItem("Chaqueta Cuero", "Estilo rockero de cuero sintético.", R.drawable.logocha, "M, L", "Negro", "$850 MXN"),
    JacketItem("Chaqueta Jeans", "Diseño casual de mezclilla.", R.drawable.logocha, "M, L, XL", "Azul", "$790 MXN"),
    JacketItem("Chaqueta Lluvia", "Impermeable con capucha.", R.drawable.logocha, "S, M, L", "Verde", "$700 MXN")
)
@Composable
fun CatalogoChaquetas(
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
                text = "Chaquetas",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            jacketCatalog.forEach { jacket ->
                JacketCard(
                    jacket = jacket,
                    carritoViewModel = carritoViewModel,
                    snackbarHostState = snackbarHostState
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
