package com.example.inktextil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavHostController, carritoViewModel: CarritoViewModel) {
    val context = LocalContext.current

    var street by remember { mutableStateOf("") }
    var number by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("México") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var paymentMethod by remember { mutableStateOf("Tarjeta") }
    var saveCard by remember { mutableStateOf(false) }

    val total by carritoViewModel.total.collectAsState()
    val carrito = carritoViewModel.carrito

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { NavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            AddressSection(street, number, postalCode, city, country,
                onStreetChange = { street = it },
                onNumberChange = { number = it },
                onPostalChange = { postalCode = it },
                onCityChange = { city = it },
                onCountryChange = { country = it }
            )

            PaymentSection(
                cardNumber, expiryDate, cvv, paymentMethod, saveCard,
                onCardNumberChange = { cardNumber = it },
                onExpiryChange = { expiryDate = it },
                onCVVChange = { cvv = it },
                onMethodChange = { paymentMethod = it },
                onSaveCardChange = { saveCard = it }
            )

            SummarySection(carrito = carrito, total = total)

            Button(
                onClick = {
                    val addressIncomplete = street.isBlank() || number.isBlank() || postalCode.isBlank() || city.isBlank() || country.isBlank()
                    val cardInvalid = paymentMethod == "Tarjeta" && (cardNumber.length != 16 || expiryDate.isBlank() || cvv.length != 3)

                    if (addressIncomplete) {
                        Toast.makeText(context, "Por favor completa todos los campos de dirección.", Toast.LENGTH_SHORT).show()
                    } else if (paymentMethod == "Tarjeta" && cardInvalid) {
                        Toast.makeText(context, "Por favor completa los datos de la tarjeta correctamente.", Toast.LENGTH_SHORT).show()
                    } else {
                        navController.navigate("confirm?street=$street&number=$number&postalCode=$postalCode&city=$city&country=$country&payment=$paymentMethod&total=${"%.2f".format(total)}")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text("Confirmar pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
@Composable
fun AddressSection(
    street: String, number: String, postalCode: String, city: String, country: String,
    onStreetChange: (String) -> Unit, onNumberChange: (String) -> Unit,
    onPostalChange: (String) -> Unit, onCityChange: (String) -> Unit,
    onCountryChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Dirección de envío", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = street, onValueChange = onStreetChange, label = { Text("Calle") },
                    modifier = Modifier.weight(0.7f))
                OutlinedTextField(
                    value = number, onValueChange = onNumberChange, label = { Text("Número") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(0.3f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = city, onValueChange = onCityChange, label = { Text("Ciudad") },
                    modifier = Modifier.weight(0.7f))
                OutlinedTextField(
                    value = postalCode, onValueChange = { if (it.length <= 5) onPostalChange(it) },
                    label = { Text("Código Postal") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(0.3f))
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = country, onValueChange = onCountryChange, label = { Text("País") },
                modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun PaymentSection(
    cardNumber: String, expiryDate: String, cvv: String,
    paymentMethod: String, saveCard: Boolean,
    onCardNumberChange: (String) -> Unit, onExpiryChange: (String) -> Unit,
    onCVVChange: (String) -> Unit, onMethodChange: (String) -> Unit,
    onSaveCardChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Método de pago", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Tarjeta", "Efectivo").forEach { method ->
                    FilterChip(
                        selected = method == paymentMethod,
                        onClick = { onMethodChange(method) },
                        label = { Text(method) }
                    )
                }
            }
            if (paymentMethod == "Tarjeta") {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = cardNumber,
                    onValueChange = { if (it.length <= 16) onCardNumberChange(it) },
                    label = { Text("Número de tarjeta") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    visualTransformation = CreditCardFilter(),
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("1234 5678 9012 3456") }
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    OutlinedTextField(
                        value = expiryDate, onValueChange = { if (it.length <= 5) onExpiryChange(it) },
                        label = { Text("MM/AA") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(
                        value = cvv, onValueChange = { if (it.length <= 3) onCVVChange(it) },
                        label = { Text("CVV") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = saveCard, onCheckedChange = onSaveCardChange)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Guardar esta tarjeta para futuras compras")
                }
            }
        }
    }
}

@Composable
fun SummarySection(carrito: List<ShirtItem>, total: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Resumen del pedido", style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            carrito.forEach { shirt ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 8.dp)) {
                    Image(
                        painter = painterResource(shirt.imageRes),
                        contentDescription = shirt.title,
                        modifier = Modifier.size(80.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(shirt.title, fontWeight = FontWeight.Bold)
                        Text("Talla: ${shirt.size}", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                        Text("Color: ${shirt.color}", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                    }
                    Text(shirt.price, fontWeight = FontWeight.Bold)
                }
            }
            Divider(modifier = Modifier.padding(vertical = 12.dp))
            PriceRow("Subtotal", "$%.2f".format(total))
            PriceRow("Envío", "$0.00")
            PriceRow("Descuento", "-$0.00")
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Total", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("$%.2f MXN".format(total), style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun PriceRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        Text(value)
    }
}

class CreditCardFilter : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var out = ""
        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 4 == 3 && i != 15) out += " "
        }
        val creditCardOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when (offset) {
                    in 0..3 -> offset
                    in 4..7 -> offset + 1
                    in 8..11 -> offset + 2
                    in 12..15 -> offset + 3
                    else -> 19
                }
            }
            override fun transformedToOriginal(offset: Int): Int {
                return when (offset) {
                    in 0..4 -> offset
                    in 5..9 -> offset - 1
                    in 10..14 -> offset - 2
                    in 15..19 -> offset - 3
                    else -> 16
                }
            }
        }
        return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
    }
}
