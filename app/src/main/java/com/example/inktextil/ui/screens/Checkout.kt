package com.example.inktextil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inktextil.model.*
import com.example.inktextil.model.ShirtItem
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import com.example.inktextil.ui.model.CarritoViewModel

@Composable
fun CheckoutScreen(
    navController: NavHostController,
    carritoViewModel: CarritoViewModel,
    userViewModel: UsuarioViewModel = viewModel()
) {
    val context = LocalContext.current
    val carrito by remember { derivedStateOf { carritoViewModel.carrito } }
    val total by carritoViewModel.total.collectAsState()

    LaunchedEffect(Unit) {
        userViewModel.cargarDatosUsuario()
    }

    val usuario = userViewModel.usuarioData
    val direcciones = usuario.direcciones
    val direccionSeleccionada = usuario.direccionSeleccionada
    val metodos = usuario.metodosPago
    val metodoSeleccionado = usuario.metodoPagoSeleccionado

    var nuevaDireccion by remember { mutableStateOf(Direccion()) }
    var mostrarNuevaDireccion by remember { mutableStateOf(false) }

    var nuevoMetodo by remember { mutableStateOf(PaymentMethod(expiryMonth = "01", expiryYear = "2025")) }
    var mostrarNuevoPago by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Dirección de Envío", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            direcciones.forEachIndexed { index, dir ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { userViewModel.seleccionarDireccion(index) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (index == direccionSeleccionada) Color(0xFFE8F5E9) else Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${dir.nombreReceptor} (${dir.numeroTelefono})", fontWeight = FontWeight.Bold)
                        Text("${dir.calle} ${dir.numero}, ${dir.colonia}, ${dir.ciudad}, CP ${dir.codigoPostal}")
                    }
                }
            }

            OutlinedButton(
                onClick = { mostrarNuevaDireccion = !mostrarNuevaDireccion },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar nueva dirección")
            }

            if (mostrarNuevaDireccion) {
                Column {
                    CustomTextField("Nombre del receptor", nuevaDireccion.nombreReceptor) {
                        nuevaDireccion = nuevaDireccion.copy(nombreReceptor = it)
                    }
                    CustomTextField("Teléfono", nuevaDireccion.numeroTelefono) {
                        nuevaDireccion = nuevaDireccion.copy(numeroTelefono = it)
                    }
                    CustomTextField("Calle", nuevaDireccion.calle) {
                        nuevaDireccion = nuevaDireccion.copy(calle = it)
                    }
                    CustomTextField("Número", nuevaDireccion.numero) {
                        nuevaDireccion = nuevaDireccion.copy(numero = it)
                    }
                    CustomTextField("Colonia", nuevaDireccion.colonia) {
                        nuevaDireccion = nuevaDireccion.copy(colonia = it)
                    }
                    CustomTextField("Ciudad", nuevaDireccion.ciudad) {
                        nuevaDireccion = nuevaDireccion.copy(ciudad = it)
                    }
                    CustomTextField("Código Postal", nuevaDireccion.codigoPostal) {
                        nuevaDireccion = nuevaDireccion.copy(codigoPostal = it)
                    }
                    Button(
                        onClick = {
                            if (nuevaDireccion.nombreReceptor.isBlank() || nuevaDireccion.numeroTelefono.isBlank()) {
                                Toast.makeText(context, "Faltan campos", Toast.LENGTH_SHORT).show()
                            } else {
                                userViewModel.agregarDireccion(nuevaDireccion)
                                nuevaDireccion = Direccion()
                                mostrarNuevaDireccion = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar dirección")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Método de Pago", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            metodos.forEachIndexed { index, metodo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { userViewModel.seleccionarMetodoPago(index) },
                    colors = CardDefaults.cardColors(
                        containerColor = if (index == metodoSeleccionado) Color(0xFFBBDEFB) else Color.White
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("•••• ${metodo.lastFourDigits}", fontWeight = FontWeight.Bold)
                        Text("Vence: ${metodo.expiryMonth}/${metodo.expiryYear}")
                        Text("Dirección: ${metodo.address}")
                    }
                }
            }

            OutlinedButton(
                onClick = { mostrarNuevoPago = !mostrarNuevoPago },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar nuevo método de pago")
            }

            if (mostrarNuevoPago) {
                Column {
                    CustomTextField("Nombre en tarjeta", nuevoMetodo.nameOnCard) {
                        nuevoMetodo = nuevoMetodo.copy(nameOnCard = it)
                    }
                    CustomTextField("Número de tarjeta", nuevoMetodo.cardNumber) {
                        if (it.length <= 16)
                            nuevoMetodo = nuevoMetodo.copy(cardNumber = it, lastFourDigits = it.takeLast(4))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        DropdownField(
                            label = "Mes",
                            options = (1..12).map { it.toString().padStart(2, '0') },
                            selected = nuevoMetodo.expiryMonth,
                            onSelected = { nuevoMetodo = nuevoMetodo.copy(expiryMonth = it) },
                            modifier = Modifier.weight(1f)
                        )
                        DropdownField(
                            label = "Año",
                            options = (2024..2030).map { it.toString() },
                            selected = nuevoMetodo.expiryYear,
                            onSelected = { nuevoMetodo = nuevoMetodo.copy(expiryYear = it) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    CustomTextField("Dirección", nuevoMetodo.address) {
                        nuevoMetodo = nuevoMetodo.copy(address = it)
                    }
                    Button(
                        onClick = {
                            if (nuevoMetodo.cardNumber.length == 16) {
                                userViewModel.agregarMetodoPago(nuevoMetodo)
                                nuevoMetodo = PaymentMethod(expiryMonth = "01", expiryYear = "2025")
                                mostrarNuevoPago = false
                            } else {
                                Toast.makeText(context, "Número de tarjeta inválido", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar tarjeta")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            SummarySection(carrito, total)

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val direccion = direcciones.getOrNull(direccionSeleccionada ?: -1)
                    val metodo = metodos.getOrNull(metodoSeleccionado ?: -1)

                    if (direccion == null || metodo == null) {
                        Toast.makeText(context, "Selecciona dirección y método de pago", Toast.LENGTH_SHORT).show()
                        return@Button
                    }

                    val pedido = Pedido(
                        productos = carrito,
                        direccion = direccion,
                        metodoPago = metodo,
                        total = total
                    )

                    Toast.makeText(context, "Guardando pedido...", Toast.LENGTH_SHORT).show()

                    userViewModel.guardarPedido(
                        pedido,
                        onSuccess = { id ->
                            println("✅ Pedido guardado con id: $id")
                            navController.navigate("confirmPedido/$id") {
                                popUpTo("checkout") { inclusive = true }
                            }
                        },
                        onError = {
                            Toast.makeText(context, "Error al guardar el pedido", Toast.LENGTH_SHORT).show()
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A237E),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Finalizar compra", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

        }
    }
}

@Composable
fun SummarySection(carrito: List<ShirtItem>, total: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Resumen del Pedido", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            carrito.forEach {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(it.title, fontWeight = FontWeight.Medium)
                    Text("Talla: ${it.size} - Color: ${it.color}")
                    Text("Precio: ${it.price}")
                }
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Text("Total: $${"%.2f".format(total)} MXN", fontWeight = FontWeight.Bold, color = Color(0xFF1A237E))
        }
    }
}

@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selected: String,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach {
                DropdownMenuItem(text = { Text(it) }, onClick = {
                    onSelected(it)
                    expanded = false
                })
            }
        }
    }
}

