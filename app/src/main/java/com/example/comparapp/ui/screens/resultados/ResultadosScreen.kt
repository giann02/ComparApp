package com.example.comparapp.ui.screens.resultados

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import android.util.Log

@Composable
fun ResultadosScreen(
    onBack: () -> Unit,
    viewModel: ResultadosViewModel = viewModel(factory = ResultadosViewModel.factory()),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    ResultadosContent(
        estado = viewModel.estado,
        onBack = onBack,
        onReintentar = viewModel::calcularPrecios,
        onSeleccionarClick = { proveedor ->
            viewModel.onSeleccionarClick(proveedor)
            val estado = viewModel.estado
            val lat1 = estado.latOrigen ?: return@ResultadosContent
            val lon1 = estado.lonOrigen ?: return@ResultadosContent
            val lat2 = estado.latDestino ?: return@ResultadosContent
            val lon2 = estado.lonDestino ?: return@ResultadosContent

            fun fmt(v: Double) = String.format("%.6f", v)

            when {
                proveedor.nombre.contains("Uber") -> {
                    val deepLink = "uber://?action=setPickup" +
                        "&pickup[latitude]=${fmt(lat1)}&pickup[longitude]=${fmt(lon1)}" +
                        "&dropoff[latitude]=${fmt(lat2)}&dropoff[longitude]=${fmt(lon2)}"
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(deepLink)))
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://m.uber.com/ul/")))
                    }
                }
                proveedor.nombre.contains("Cabify") -> {
                    val deepLink = "cabify://cabify.com/ride" +
                        "?stops[0][loc]=${fmt(lat1)},${fmt(lon1)}" +
                        "&stops[1][loc]=${fmt(lat2)},${fmt(lon2)}"
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(deepLink)))
                    } catch (e: ActivityNotFoundException) {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://cabify.com/")))
                    }
                }
                proveedor.nombre.contains("DiDi") -> {
                    val diDiPackages = listOf("com.didiglobal.passenger", "com.didi.rider")
                    val diDiIntent = diDiPackages.firstNotNullOfOrNull { pkg ->
                        context.packageManager.getLaunchIntentForPackage(pkg)
                    }
                    if (diDiIntent != null) {
                        context.startActivity(diDiIntent)
                    } else {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://web.didiglobal.com/")))
                    }
                }
                else -> return@ResultadosContent
            }
        },
        modifier = modifier
    )
}
