package com.example.inktextil.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inktextil.model.PaymentMethod
import com.example.inktextil.model.PaymentViewModel
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PagosScreen(
    navController: NavHostController,
    paymentViewModel: PaymentViewModel = viewModel()
) {
    var nameOnCard by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryMonth by remember { mutableStateOf("01") }
    var expiryYear by remember { mutableStateOf("2025") }
    var cvv by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val methods by paymentViewModel.paymentMethods.collectAsState()

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .navigationBarsPadding()
            ) {
                NavBar(navController)
            }
        }
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
                Text("Tus métodos de pago", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }

            items(methods) { method ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { /* seleccionar */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("•••• ${method.lastFourDigits}", fontSize = 18.sp, color = Color.Black)
                        Text("Nombre: ${method.nameOnCard}", color = Color.Black)
                        Text("Expira: ${method.expiryMonth}/${method.expiryYear}", color = Color.Black)
                        Text("Dirección: ${method.address}", color = Color.Black)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            "Eliminar",
                            color = Color.Red,
                            modifier = Modifier.clickable {
                                paymentViewModel.deletePaymentMethod(method.lastFourDigits)
                            }
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                Text("Agregar nuevo método", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            }

            item {
                TextField(
                    value = nameOnCard,
                    onValueChange = { nameOnCard = it },
                    label = { Text("Nombre en la tarjeta") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                    textStyle = TextStyle(color = Color.Black)
                )
            }

            item {
                TextField(
                    value = cardNumber,
                    onValueChange = { cardNumber = it },
                    label = { Text("Número de tarjeta") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                    textStyle = TextStyle(color = Color.Black)
                )
            }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
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
                    onValueChange = { cvv = it },
                    label = { Text("CVV") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                    textStyle = TextStyle(color = Color.Black)
                )
            }

            item {
                TextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Dirección") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White),
                    textStyle = TextStyle(color = Color.Black)
                )
            }

            item {
                Button(
                    onClick = {
                        val randomDigits = (1000..9999).random().toString()
                        val newMethod = PaymentMethod(
                            nameOnCard = nameOnCard,
                            expiryMonth = expiryMonth,
                            expiryYear = expiryYear,
                            address = address,
                            lastFourDigits = randomDigits
                        )
                        paymentViewModel.addPaymentMethod(newMethod)
                        // Clear fields after adding method
                        nameOnCard = ""
                        cardNumber = ""
                        expiryMonth = "01"
                        expiryYear = "2025"
                        cvv = ""
                        address = ""
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true },
            trailingIcon = {
                Icon(
                    imageVector = if (expanded) Icons.Filled.ArrowDropDown else Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
            },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(containerColor = Color.White)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
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

