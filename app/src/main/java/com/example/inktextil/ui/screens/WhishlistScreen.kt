package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
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
fun WishListScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "WISH LIST",
                color = colorResource(R.color.Blue900),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 27.sp,
                modifier = Modifier.weight(1f)
            )
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Buscar") },
                modifier = Modifier.width(200.dp)
            )
        }

        // Links
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Artículos", color = colorResource(R.color.Blue900),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp)

        }

        Spacer(modifier = Modifier.height(16.dp))


        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            items(10) { index ->
                ProductItem()
            }
        }

    }
}

@Composable
fun ProductItem() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(Color.LightGray)
        ) {
            Image(
                painter = painterResource(id = R.drawable.playera1),
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center)
            )

        }
        Text(text = "Playera Pulp Fiction",
            color= Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(top = 8.dp))
    }
}




