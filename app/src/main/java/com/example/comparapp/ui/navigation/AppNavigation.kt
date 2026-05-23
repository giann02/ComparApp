package com.example.comparapp.ui.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.comparapp.AppContainer
import com.example.comparapp.ui.screens.favoritas.FavoritasScreen
import com.example.comparapp.ui.screens.login.LoginScreen
import com.example.comparapp.ui.screens.main.MainScreen
import com.example.comparapp.ui.screens.register.RegisterScreen
import com.example.comparapp.ui.screens.resultados.ResultadosScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable("main") {
            MainScreen(
                onCompararClick = { origen, destino ->
                    val o = Uri.encode(origen.ifEmpty { "Punto de partida" })
                    val d = Uri.encode(destino.ifEmpty { "Destino" })
                    navController.navigate("resultados/$o/$d")
                },
                onVerTodas = { navController.navigate("favoritas") },
                onCerrarSesion = {
                    AppContainer.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
        composable("favoritas") {
            FavoritasScreen(
                onBack = { navController.popBackStack() },
                onRutaClick = { origen, destino ->
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "resultados/{origen}/{destino}",
            arguments = listOf(
                navArgument("origen") { type = NavType.StringType },
                navArgument("destino") { type = NavType.StringType }
            )
        ) {
            ResultadosScreen(
                onBack = { navController.popBackStack() },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
