package com.example.inktextil

import RegisterSocialMediaScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.components.NavBar
import com.example.inktextil.components.TopBar
import com.example.inktextil.screens.ProfileScreen
import com.example.inktextil.screens.RegisterScreen
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavigationGraph(navController)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("registerSocial") {
            RegisterSocialMediaScreen(
                onEmailRegister = {
                    navController.navigate("registerScreen")
                },
                onLoginClick = {
                    navController.navigate("loginScreen")
                }
            )
        }
        composable("registerScreen") { RegisterScreen(navController) }
        composable("NavBar") { NavBar(navController) }
        composable("TopBar") { TopBar(navController) }
        composable("ProfileScreen") { ProfileScreen(navController) }
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(Unit) {
        delay(2000)
        navController.navigate("registerSocial") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "INK TEXTIL",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Botón para ir al perfil
        GoToProfileButton(navController)
    }
}

@Composable
fun GoToProfileButton(navController: NavHostController) {
    Button(
        onClick = { navController.navigate("ProfileScreen") },
        modifier = Modifier.padding(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text(text = "Ir a Perfil", color = Color.White)
    }
}
