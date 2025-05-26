package com.example.inktextil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inktextil.model.PaymentMethod
import com.example.inktextil.model.PaymentViewModel
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagosScreen(
    navController: NavHostController,
    paymentViewModel: PaymentViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var nameOnCard by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryMonth by remember { mutableStateOf("01") }
    var expiryYear by remember { mutableStateOf("2025") }
    var cvv by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val methods by paymentViewModel.paymentMethods.collectAsState()

    LaunchedEffect(Unit) {
        paymentViewModel.loadPaymentMethods()
    }

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text("Tus métodos de pago", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }

            items(methods) { method ->
                PaymentCardDisplay(method) {
                    scope.launch {
                        paymentViewModel.deletePaymentMethod(method.lastFourDigits)
                        paymentViewModel.loadPaymentMethods()
                        Toast.makeText(context, "Método eliminado", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Agregar nuevo método", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            item {
                TextField(
                    value = nameOnCard,
                    onValueChange = { nameOnCard = it },
                    label = { Text("Nombre en la tarjeta") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                )
            }

            item {
                TextField(
                    value = cardNumber,
                    onValueChange = {
                        if (it.length <= 16 && it.all(Char::isDigit)) {
                            cardNumber = it
                        }
                    },
                    label = { Text("Número de tarjeta") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomDropdownField(
                        label = "Mes",
                        options = (1..12).map { it.toString().padStart(2, '0') },
                        selectedOption = expiryMonth,
                        onOptionSelected = { expiryMonth = it },
                        modifier = Modifier.weight(1f)
                    )
                    CustomDropdownField(
                        label = "Año",
                        options = (2025..2035).map { it.toString() },
                        selectedOption = expiryYear,
                        onOptionSelected = { expiryYear = it },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                TextField(
                    value = cvv,
                    onValueChange = { if (it.length <= 4 && it.all(Char::isDigit)) cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                )
            }

            item {
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección de facturación") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                )
            }

            item {
                Button(
                    onClick = {
                        if (cardNumber.length == 16) {
                            val method = PaymentMethod(
                                nameOnCard = nameOnCard,
                                cardNumber = cardNumber,
                                lastFourDigits = cardNumber.takeLast(4),
                                expiryMonth = expiryMonth,
                                expiryYear = expiryYear,
                                address = address
                            )
                            paymentViewModel.addPaymentMethod(method)
                            nameOnCard = ""
                            cardNumber = ""
                            expiryMonth = "01"
                            expiryYear = "2025"
                            cvv = ""
                            address = ""
                            scope.launch { paymentViewModel.loadPaymentMethods() }
                            Toast.makeText(context, "Método agregado", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Número inválido", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Guardar método de pago")
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = { Icon(Icons.Filled.ArrowDropDown, null) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
        )

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}
@Composable
fun PaymentCardDisplay(
    method: PaymentMethod,
    onDelete: () -> Unit
) {
    val cardType = when {
        method.cardNumber.startsWith("4") -> "Visa"
        method.cardNumber.startsWith("5") -> "MasterCard"
        method.cardNumber.startsWith("3") -> "Amex"
        else -> "Genérica"
    }

    val gradient = when (cardType) {
        "Visa" -> Brush.linearGradient(colors = listOf(Color(0xFF1A237E), Color(0xFF3949AB)))
        "MasterCard" -> Brush.linearGradient(colors = listOf(Color(0xFFD32F2F), Color(0xFFFF8A65)))
        "Amex" -> Brush.linearGradient(colors = listOf(Color(0xFF2E7D32), Color(0xFF66BB6A)))
        else -> Brush.linearGradient(colors = listOf(Color.DarkGray, Color.Gray))
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "•••• ${method.lastFourDigits}",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(method.nameOnCard, color = Color.White, fontSize = 16.sp)
                Text("Expira: ${method.expiryMonth}/${method.expiryYear}", color = Color.White)
                Text("Dirección: ${method.address}", color = Color.White)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "Eliminar",
                    color = Color.Yellow,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onDelete() }
                )
            }
        }
    }
}


