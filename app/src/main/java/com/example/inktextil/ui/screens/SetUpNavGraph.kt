package com.example.inktextil.ui.screens

import ArticlesScreen
import RegisterSocialMediaScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
    fun SetUpNavGraph(navController: NavHostController, modifier: Modifier) {
        NavHost(navController = navController, startDestination = "splash") {

            composable("splash") { SplashScreen(navController) }
            composable("login") { LoginScreen(navController) }
            composable("RegisterScreen") { RegisterScreen(navController) }
            composable("registerSocialMedia") { RegisterSocialMediaScreen(navController) }
            composable("menu") { Menu(navController) }
            composable("articles") { ArticlesScreen(navController) }
            composable("datos") { DatosScreen(navController) }
            composable("detallesArticulo") { DetallesArticulo(navController) }
            composable("pedidos") { PedidosScreen(navController) }
            composable("perfilArticulos") { InicioArticulo(navController) }
            composable("profileArticles") { ProfileScreen(navController) }
            composable("wishlist") { WishListScreen(navController) }
            composable("detallepedidos") { DetallesPedidoScreen(navController) }
            composable("carrito") { CarritoScreen(navController) }
            composable("historial") { HistorialScreen(navController) }
            composable("misdiseños") { DisenoScreen(navController) }
            composable("forgot") { ForgotPasswordScreen(navController) }
            composable("pagos") { PagosScreen(navController) }


        }
    }
