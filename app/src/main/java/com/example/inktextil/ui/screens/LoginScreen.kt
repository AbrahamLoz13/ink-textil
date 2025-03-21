package com.example.inktextil.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.lint.kotlin.metadata.Visibility
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.R

@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) } // Para controlar la visibilidad de la contraseña

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
            leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Mostrar/Ocultar Contraseña"
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = { /* Acción de recuperar contraseña */ }) {
            Text("¿Olvidaste tu contraseña?", color = Color.Blue)
        }

        Button(
            onClick = { /* Acción de inicio de sesión */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ingresar")
        }

        Spacer(modifier = Modifier.height(25.dp))
        Text("Registrarse via...", color = Color.Blue)

        Spacer(modifier = Modifier.height(8.dp))
        SocialButton(iconRes = R.drawable.img_1, text = "Registrarse via Google", bgColor = Color(0xFF4285F4))
        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(8.dp))
        SocialButton(iconRes = R.drawable.img_2, text = "Registrarse via X", bgColor = Color(0xFF1C1C1C))
        Spacer(modifier = Modifier.height(12.dp))

        Spacer(modifier = Modifier.height(8.dp))
        SocialButton(iconRes = R.drawable.img, text = "Registrarse via Gmail", bgColor = Color(0xFFD93025))
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun SocialButton(
    iconRes: Int,
    text: String,
    bgColor: Color,
    textOffsetX: Dp = 20.dp, // Ajuste horizontal
    textOffsetY: Dp = 0.dp  // Ajuste vertical
) {
    Button(
        onClick = { /* Acción del botón */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícono dentro de un círculo
            Box(
                modifier = Modifier
                    .size(32.dp)  // Tamaño del círculo
                    .clip(CircleShape)
                    .background(Color.White), // Fondo blanco para mejor visibilidad
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp) // Tamaño del icono dentro del círculo
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Contenedor para el texto con desplazamiento ajustable
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .offset(textOffsetX, textOffsetY), // Aquí se define la posición
                contentAlignment = Alignment.CenterStart // Puedes cambiar a Center, End, etc.
            ) {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

// Vista previa
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewLoginScreen() {
    val navController = rememberNavController()
    LoginScreen(navController = navController)
}
