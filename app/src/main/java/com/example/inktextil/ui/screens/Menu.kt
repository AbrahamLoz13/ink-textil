package com.example.inktextil.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(200.dp))

        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Datos")
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Pedidos")
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Pagos")
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Mis diseños")
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Wish list")
        }
        Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
            Text("Historial")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
