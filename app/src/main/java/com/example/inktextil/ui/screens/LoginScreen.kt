package com.example.inktextil.ui.screens

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.inktextil.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.OAuthProvider

@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(context, gso)

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.result
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { authResult ->
                        if (authResult.isSuccessful) {
                            Toast.makeText(context, "Inicio de sesión con Google exitoso", Toast.LENGTH_SHORT).show()
                            navController.navigate("articles") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            Toast.makeText(context, "Error con Google: ${authResult.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        } else {
            Toast.makeText(context, "Error al iniciar sesión con Google.", Toast.LENGTH_SHORT).show()
        }
    }

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .horizontalScroll(horizontalScrollState)
            .verticalScroll(verticalScrollState),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Iniciar Sesión", fontSize = 24.sp, fontWeight = FontWeight.Bold)

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Correo electrónico") },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(onClick = { navController.navigate("forgot") }) {
                Text("¿Olvidaste tu contraseña?")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (username.isNotBlank() && password.isNotBlank()) {
                        auth.signInWithEmailAndPassword(username, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                                    navController.navigate("articles") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                } else {
                                    Toast.makeText(context, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                    } else {
                        Toast.makeText(context, "Rellena los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ingresar")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text("Registrarse vía...")

            Spacer(modifier = Modifier.height(12.dp))

            // Google
            SocialButton(
                iconRes = R.drawable.img_1,
                text = "Google",
                bgColor = Color(0xFF4285F4),
                onClick = {
                    val signInIntent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Twitter
            SocialButton(
                iconRes = R.drawable.img_2,
                text = "X",
                bgColor = Color(0xFF1C1C1C),
                onClick = {
                    val provider = OAuthProvider.newBuilder("twitter.com")
                    val pendingResultTask = auth.pendingAuthResult
                    if (pendingResultTask != null) {
                        pendingResultTask
                            .addOnSuccessListener {
                                navController.navigate("articles")
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Error en el inicio de sesión. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        auth.startActivityForSignInWithProvider(context as Activity, provider.build())
                            .addOnSuccessListener {
                                navController.navigate("articles")
                            }
                            .addOnFailureListener { exception ->
                                Toast.makeText(context, "Error: ${exception.localizedMessage}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextButton(onClick = { navController.navigate("RegisterScreen") }) {
                Text("¿No tienes cuenta? Regístrate")
            }
        }
    }
}

@Composable
fun SocialButton(
    iconRes: Int,
    text: String,
    bgColor: Color,
    onClick: () -> Unit = {},
    textOffsetX: Dp = 20.dp,
    textOffsetY: Dp = 0.dp
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = ButtonDefaults.buttonColors(containerColor = bgColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                fontSize = 16.sp,
                color = Color.White
            )
        }
    }
}
