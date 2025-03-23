package com.example.inktextil.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@Composable
fun Menu(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            MenuButton(text = "Datos", onClick = { navController.navigate("datos") })
            MenuButton(text = "Pedidos", onClick = { navController.navigate("pedidos") })
            MenuButton(text = "Pagos", onClick = { navController.navigate("pagos") })
            MenuButton(text = "Mis diseños", onClick = { navController.navigate("mis_diseños") })
            MenuButton(text = "Wish list", onClick = { navController.navigate("wishlist") })
            MenuButton(text = "Historial", onClick = { navController.navigate("historial") })
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Text(text, style = MaterialTheme.typography.bodyLarge)
    }
}