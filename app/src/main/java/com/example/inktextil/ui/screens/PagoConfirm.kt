package com.example.inktextil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inktextil.R
import com.example.inktextil.model.Pedido
import com.example.inktextil.model.UsuarioViewModel
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun PagoConfirmScreen(
    navController: NavHostController,
    pedidoId: String,
    userViewModel: UsuarioViewModel = viewModel()
) {
    val context = LocalContext.current
    var pedido by remember { mutableStateOf<Pedido?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // ✅ Cargar pedido desde Firebase
    LaunchedEffect(pedidoId) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (uid != null) {
            Firebase.firestore
                .collection("usuarios")
                .document(uid)
                .collection("pedidos")
                .document(pedidoId)
                .get()
                .addOnSuccessListener { doc ->
                    val pedidoFirebase = doc.toObject(Pedido::class.java)
                    if (pedidoFirebase != null) {
                        pedido = pedidoFirebase
                    }
                    isLoading = false
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error al cargar el pedido", Toast.LENGTH_SHORT).show()
                    isLoading = false
                }
        } else {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            pedido?.let { pedido ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    Image(
                        painter = painterResource(id = R.drawable.img_3),
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
                        text = "Gracias por tu compra. Tu pedido ha sido confirmado.",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 24.dp),
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.3f))

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Detalles del Pedido", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                        pedido.productos.forEach { item ->
                            Column {
                                Text("Producto: ${item.title}", fontWeight = FontWeight.Medium)
                                Text("Talla: ${item.size}")
                                Text("Color: ${item.color}")
                                Text("Precio: ${item.price}")
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }

                        Divider()

                        Text("Dirección:", fontWeight = FontWeight.Bold)
                        Text("${pedido.direccion.nombreReceptor} (${pedido.direccion.numeroTelefono})")
                        Text("${pedido.direccion.calle} ${pedido.direccion.numero}, ${pedido.direccion.ciudad}, CP ${pedido.direccion.codigoPostal}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text("Método de Pago:", fontWeight = FontWeight.Bold)
                        Text("Tarjeta: •••• ${pedido.metodoPago.lastFourDigits}")
                        Text("Vence: ${pedido.metodoPago.expiryMonth}/${pedido.metodoPago.expiryYear}")
                        Text("Facturación: ${pedido.metodoPago.address}")

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Total: \$${"%.2f".format(pedido.total)} MXN",
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            navController.navigate("articles") {
                                popUpTo("checkout") { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(48.dp)
                    ) {
                        Text("Seguir comprando", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontró el pedido.", color = Color.Gray)
                }
            }
        }
    }
}
