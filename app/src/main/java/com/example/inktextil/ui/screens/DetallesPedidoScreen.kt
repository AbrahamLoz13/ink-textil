package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inktextil.R

@Preview (showBackground = true)
@Composable
fun DetallesPedidoScreen(){
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "MIS PEDIDOS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
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
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {  }) {
                    Text("Buscar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "PLAYERA PULP FICTION",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.LightGray)
                ) {
                    Image(
                        painter = painterResource(R.drawable.playera1),
                        contentDescription = "Imagen del pedido",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Este diseño es ideal para personas para personas amantes del cine",
                    modifier = Modifier.width(180.dp),
                    textAlign = TextAlign.Justify
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(Color.Gray)
            ) {
                Text("Volver a comprar")
            }

            Spacer(modifier = Modifier.height(16.dp))


            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("DATOS DE ENVÍO")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Text("DATOS DE PAGO")
                }
            }

            Spacer(modifier = Modifier.weight(1f))


        }
    }

