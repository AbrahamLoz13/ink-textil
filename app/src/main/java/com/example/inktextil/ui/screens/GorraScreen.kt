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
import com.example.inktextil.ui.components.CapCard
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import com.example.inktextil.ui.model.CarritoViewModel

// Modelo de gorra
data class CapItem(
    val title: String,
    val description: String,
    val imageRes: Int,
    val size: String,
    val color: String,
    val price: String
)

val capCatalog = listOf(
    CapItem("Gorra Marvel", "Diseño de los Avengers.", R.drawable.gorralog, "Única", "Rojo", "$180 MXN"),
    CapItem("Gorra Dragon Ball", "Diseño de Goku.", R.drawable.gorralog, "Única", "Negro", "$190 MXN"),
    CapItem("Gorra NASA", "Estilo espacial moderno.", R.drawable.gorralog, "Única", "Blanco", "$200 MXN")
)
@Composable
fun CatalogoGorras(
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
                text = "Gorras",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            capCatalog.forEach { cap ->
                CapCard(
                    cap = cap,
                    carritoViewModel = carritoViewModel,
                    snackbarHostState = snackbarHostState
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
