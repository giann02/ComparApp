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
import com.example.comparapp.ui.screens.facedetection.FaceDetectionScreen
import com.example.comparapp.ui.screens.olvidarcontrasena.OlvidarContrasenaScreen
import com.example.comparapp.ui.screens.favoritas.FavoritasScreen
import com.example.comparapp.ui.screens.historial.HistorialAhorrosScreen
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
                },
                onOlvidarContrasena = {
                    navController.navigate("olvidar_contrasena")
                }
            )
        }
        composable("olvidar_contrasena") {
            OlvidarContrasenaScreen(
                onExito = {
                    navController.navigate("login") {
                        popUpTo("olvidar_contrasena") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
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
                    navController.navigate("facedetection/$o/$d")
                },
                onVerTodas = { navController.navigate("favoritas") },
                onVerHistorial = { navController.navigate("historial_ahorros") },
                onCerrarSesion = {
                    AppContainer.cerrarSesion()
                    navController.navigate("login") {
                        popUpTo("main") { inclusive = true }
                    }
                }
            )
        }
        composable(
            route = "facedetection/{origen}/{destino}",
            arguments = listOf(
                navArgument("origen") { type = NavType.StringType },
                navArgument("destino") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val origen = backStackEntry.arguments?.getString("origen") ?: ""
            val destino = backStackEntry.arguments?.getString("destino") ?: ""
            FaceDetectionScreen(
                onConfirmed = {
                    navController.navigate("resultados/$origen/$destino") {
                        popUpTo("facedetection/$origen/$destino") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable("historial_ahorros") {
            HistorialAhorrosScreen(onBack = { navController.popBackStack() })
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
