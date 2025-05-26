package com.example.inktextil.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.inktextil.model.Direccion
import com.example.inktextil.model.UsuarioViewModel
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import kotlinx.coroutines.launch

@Composable
fun DatosScreen(navController: NavHostController, viewModel: UsuarioViewModel = viewModel()) {
    val usuario = viewModel.usuarioData
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var calle by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var colonia by remember { mutableStateOf("") }
    var ciudad by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }

    var correo by remember { mutableStateOf(usuario.correo) }
    var username by remember { mutableStateOf(usuario.username) }

    LaunchedEffect(Unit) {
        viewModel.cargarDatosUsuario()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TopBar(navController = navController)

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // 👉 DATOS PERSONALES
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = username,
                                onValueChange = {
                                    username = it
                                    viewModel.actualizarCampo("username", it)
                                },
                                placeholder = { Text("Username", color = Color.Black) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(12.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedContainerColor = Color(0xFFFDFDFD),
                                    unfocusedContainerColor = Color(0xFFFDFDFD),
                                    focusedIndicatorColor = Color(0xFF6200EE),
                                    unfocusedIndicatorColor = Color(0xFFBDBDBD)
                                )
                            )
                            Button(
                                onClick = {
                                    viewModel.guardarDatosUsuario()
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Datos guardados con éxito")
                                    }
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF6200EE),
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Guardar")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = correo,
                            onValueChange = {
                                correo = it
                                viewModel.actualizarCampo("correo", it)
                            },
                            placeholder = { Text("Correo", color = Color.Black) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = Color(0xFFFDFDFD),
                                unfocusedContainerColor = Color(0xFFFDFDFD),
                                focusedIndicatorColor = Color(0xFF6200EE),
                                unfocusedIndicatorColor = Color(0xFFBDBDBD)
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Direcciones Guardadas", style = MaterialTheme.typography.titleMedium)

                usuario.direcciones.forEachIndexed { index, direccion ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable { viewModel.seleccionarDireccion(index) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (index == usuario.direccionSeleccionada)
                                Color(0xFFE3F2FD) else Color.White
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text("Calle: ${direccion.calle}")
                            Text("Número: ${direccion.numero}")
                            Text("Colonia: ${direccion.colonia}")
                            Text("Ciudad: ${direccion.ciudad}")
                            Text("CP: ${direccion.codigoPostal}")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text("Agregar nueva dirección", style = MaterialTheme.typography.titleMedium)

                CustomTextField("Calle", calle) { calle = it }
                CustomTextField("Número", numero) { numero = it }
                CustomTextField("Colonia", colonia) { colonia = it }
                CustomTextField("Ciudad", ciudad) { ciudad = it }
                CustomTextField("C.P.", cp) { cp = it }

                Button(
                    onClick = {
                        if (calle.isNotBlank() && numero.isNotBlank() && colonia.isNotBlank() && ciudad.isNotBlank() && cp.isNotBlank()) {
                            val nueva = Direccion(
                                calle = calle,
                                numero = numero,
                                colonia = colonia,
                                ciudad = ciudad,
                                codigoPostal = cp
                            )
                            viewModel.agregarDireccion(nueva)
                            calle = ""; numero = ""; colonia = ""; ciudad = ""; cp = ""
                            scope.launch {
                                snackbarHostState.showSnackbar("Dirección agregada")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                ) {
                    Text("Agregar Dirección")
                }
            }

            NavBar(navController = navController)
        }
    }
}

@Composable
fun CustomTextField(label: String, value: String, onChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onChange,
        placeholder = { Text(label, color = Color.Black) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = Color(0xFFFDFDFD),
            unfocusedContainerColor = Color(0xFFFDFDFD),
            focusedIndicatorColor = Color(0xFF6200EE),
            unfocusedIndicatorColor = Color(0xFFBDBDBD)
        )
    )
}
