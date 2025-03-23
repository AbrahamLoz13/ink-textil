package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inktextil.R


@Preview(showBackground = true)
@Composable
fun HistorialScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "HISTORIAL",
            color = colorResource(R.color.Blue900),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar pedido") },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {  },
                modifier = Modifier.height(50.dp)
            ) {
                Text("Buscar")
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(8) { index ->
                PedidosItem(index)
            }
        }

    }
}

@Composable
fun PedidosItem(index: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, Color.Gray)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Playera Pulp Fiction",
            fontWeight = FontWeight.SemiBold,
            fontSize = 15.sp,
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = R.drawable.playera1),
            contentDescription = "Imagen del producto",
            modifier = Modifier
                .size(80.dp)
                .background(Color.LightGray)
        )
    }
}
