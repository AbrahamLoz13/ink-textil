package com.example.inktextil.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.model.UsuarioData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var email by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var showSuccessDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = {
                showSuccessDialog = false
                navController.navigate("login")
            },
            title = { Text("Registro exitoso") },
            text = { Text("Tu cuenta ha sido creada correctamente.") },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.navigate("login")
                    }
                ) {
                    Text("Aceptar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registro",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {val emailText = email.text.trim()
                val passwordText = password.text
                val usernameText = username.text.trim()

                if (emailText.isNotEmpty() && passwordText.isNotEmpty() && usernameText.isNotEmpty()) {
                    Firebase.auth.createUserWithEmailAndPassword(emailText, passwordText)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = Firebase.auth.currentUser
                                val uid = user?.uid
                                val db = Firebase.firestore

                                val nuevoUsuario = UsuarioData(
                                    username = usernameText,
                                    correo = emailText
                                    // el resto de los campos quedan como por defect
                                )

                                if (uid != null) {
                                    db.collection("usuarios").document(uid)
                                        .set(nuevoUsuario)
                                        .addOnSuccessListener {
                                            showSuccessDialog = true
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                context,
                                                "Error al guardar en Firestore: ${e.message}",
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                } else {
                                    Toast.makeText(context, "No se pudo obtener el UID", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Error: ${task.exception?.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                } else {
                    Toast.makeText(
                        context,
                        "Por favor llena todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Al registrarte, aceptas nuestros Términos de Servicio y Política de Privacidad.",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRegisterScreen() {
    val navController = rememberNavController()
    RegisterScreen(navController)
}
