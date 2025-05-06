package com.example.inktextil.ui.screens

import androidx.compose.foundation.background
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
import com.example.inktextil.model.UsuarioViewModel
import com.example.inktextil.ui.components.NavBar
import com.example.inktextil.ui.components.TopBar
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatosScreen(navController: NavHostController, viewModel: UsuarioViewModel = viewModel()) {
    val usuario = viewModel.usuarioData
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Cargar datos automáticamente al abrir la pantalla
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
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Campo Username y botón
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = usuario.username,
                                onValueChange = { viewModel.actualizarCampo("username", it) },
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

                        CustomTextField("Correo", usuario.correo) {
                            viewModel.actualizarCampo("correo", it)
                        }
                        CustomTextField("Dirección 1", usuario.direccion1) {
                            viewModel.actualizarCampo("direccion1", it)
                        }
                        CustomTextField("Dirección 2", usuario.direccion2) {
                            viewModel.actualizarCampo("direccion2", it)
                        }
                        DropdownMenuField("Ciudad", listOf("Ciudad A", "Ciudad B", "Ciudad C"), usuario.ciudad) {
                            viewModel.actualizarCampo("ciudad", it)
                        }
                        CustomTextField("C.p", usuario.cp) {
                            viewModel.actualizarCampo("cp", it)
                        }
                        DropdownMenuField("Colonia", listOf("Colonia X", "Colonia Y", "Colonia Z"), usuario.colonia) {
                            viewModel.actualizarCampo("colonia", it)
                        }
                        CustomTextField("Número", usuario.numero) {
                            viewModel.actualizarCampo("numero", it)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            NavBar(navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuField(
    label: String,
    items: List<String>,
    selected: String,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            value = selected,
            onValueChange = {},
            label = { Text(label, color = Color.Black) },
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                focusedContainerColor = Color(0xFFFDFDFD),
                unfocusedContainerColor = Color(0xFFFDFDFD),
                focusedIndicatorColor = Color(0xFF6200EE),
                unfocusedIndicatorColor = Color(0xFFBDBDBD)
            ),
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item, color = Color.Black) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    }
                )
            }
        }
    }
}
