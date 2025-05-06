package com.example.inktextil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.inktextil.ui.model.CarritoViewModel
import com.example.inktextil.ui.screens.SetUpNavGraph
import com.example.inktextil.ui.theme.InkTextilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InkTextilTheme {
                val navController = rememberNavController()
                val carritoViewModel: CarritoViewModel = viewModel() // ✅ ViewModel declarado

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { paddingValues ->
                    SetUpNavGraph(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues),
                        carritoViewModel = carritoViewModel // ✅ Pasado al NavGraph
                    )
                }
            }
        }
    }
}
