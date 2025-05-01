package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.R
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@Composable
fun PagoConfirm(navController: NavHostController) {
    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen de confirmación
            Image(
                painter = painterResource(id = R.drawable.img_3), // Asegúrate de tener esta imagen
                contentDescription = "Pago Confirmado",
                modifier = Modifier.size(150.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "¡Pago realizado con éxito!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Gracias por tu compra. Tu pedido ha sido confirmado y será enviado pronto.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 24.dp),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Divider(
                modifier = Modifier.padding(horizontal = 24.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Detalles del pedido (ejemplo)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Producto: Playera Premium", fontWeight = FontWeight.SemiBold)
                Text("Talla: M")
                Text("Color: Negro")
                Text("Total: $350.00 MXN", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    // Redirigir al home u otra pantalla
                    navController.navigate("articles") {
                        popUpTo("checkout") { inclusive = true } // Opcional, limpia el stack
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(48.dp)
            ) {
                Text("Seguir comprando", fontSize = 16.sp)
            }
        }
    }
}
