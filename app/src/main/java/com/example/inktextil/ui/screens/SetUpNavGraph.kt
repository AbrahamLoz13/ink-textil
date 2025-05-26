package com.example.inktextil.ui.screens

import RegisterSocialMediaScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.inktextil.ui.model.CarritoViewModel

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    carritoViewModel: CarritoViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "splash",
        modifier = modifier
    ) {

        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("RegisterScreen") { RegisterScreen(navController) }
        composable("registerSocialMedia") { RegisterSocialMediaScreen(navController) }

        composable("menu") { Menu(navController) }
        composable("articles") { ArticlesScreen(navController) }
        composable("datos") { DatosScreen(navController) }
        composable("detallesArticulo") { CatalogoPlayeras(navController, carritoViewModel) }
        composable("pedidos") { PedidosScreen(navController) }
        composable("wishlist") { WishListScreen(navController, carritoViewModel) }
        composable("detallepedidos") { DetallesPedidoScreen(navController) }

        composable("carrito") { CarritoScreen(navController, carritoViewModel) }
        composable("historial") { HistorialScreen(navController) }
        composable("misdiseños") { DisenoScreen(navController = navController) }
        composable("forgot") { ForgotPasswordScreen(navController) }
        composable("pagos") { PagosScreen(navController) }

        composable("sudaderasScreen") { CatalogoSudaderas(navController, carritoViewModel) }
        composable("pantalonesScreen") { CatalogoPantalones(navController, carritoViewModel) }
        composable("gorrasScreen") { CatalogoGorras(navController, carritoViewModel) }
        composable("chaquetasScreen") { CatalogoChaquetas(navController, carritoViewModel) }

        composable("checkout") { CheckoutScreen(navController, carritoViewModel) }

        // ✅ Ruta con ID de confirmación
        composable("confirmPedido/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            PagoConfirmScreen(navController, id)
        }

        // ❌ Eliminado: confirm sin parámetros innecesario
        // composable("confirm") { PagoConfirmScreen(navController, carritoViewModel) }
    }
}
