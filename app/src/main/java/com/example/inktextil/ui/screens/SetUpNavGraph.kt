package com.example.inktextil.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
@Composable
fun SetUpNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        // Agrega más pantallas aquí si las necesitas
    }
}
