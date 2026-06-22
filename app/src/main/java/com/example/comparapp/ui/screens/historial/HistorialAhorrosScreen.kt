package com.example.comparapp.ui.screens.historial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.comparapp.AppContainer
import com.example.comparapp.domain.model.Ahorro
import com.example.comparapp.domain.repository.AhorroRepository
import com.example.comparapp.ui.components.ComparAppLogo
import com.example.comparapp.ui.theme.BackgroundColor
import com.example.comparapp.ui.theme.ComparBlue
import com.example.comparapp.ui.theme.SurfaceColor
import com.example.comparapp.ui.theme.TextHint
import com.example.comparapp.ui.theme.TextLabel
import com.example.comparapp.ui.theme.TextSecondary
import kotlinx.coroutines.flow.Flow
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Verde = Color(0xFF16A34A)

private fun acortarDireccion(direccion: String): String =
    direccion.substringBefore(",").trim().split(" ").take(3).joinToString(" ")

class HistorialAhorrosViewModel(repository: AhorroRepository) : ViewModel() {
    private val usuarioId = AppContainer.usuarioActual.value?.id ?: 0
    val viajes: Flow<List<Ahorro>> = repository.obtenerTodos(usuarioId)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialAhorrosScreen(
    onBack: () -> Unit,
    viewModel: HistorialAhorrosViewModel = viewModel(
        factory = viewModelFactory {
            initializer { HistorialAhorrosViewModel(AppContainer.ahorroRepository) }
        }
    )
) {
    val viajes by viewModel.viajes.collectAsState(initial = emptyList())

    Scaffold(
        containerColor = BackgroundColor,
        topBar = {
            TopAppBar(
                title = { ComparAppLogo() },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = TextLabel)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Text(
                "Historial de Ahorros",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = ComparBlue
            )

            Spacer(Modifier.height(16.dp))

            if (viajes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Todavía no comparaste ningún viaje",
                        fontSize = 14.sp,
                        color = TextHint
                    )
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(viajes) { viaje ->
                        TarjetaViajeHistorial(viaje = viaje)
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}

@Composable
private fun TarjetaViajeHistorial(viaje: Ahorro) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(BackgroundColor, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.DirectionsCar,
                    contentDescription = null,
                    tint = ComparBlue,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    acortarDireccion(viaje.origen),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = TextLabel,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "→ ${acortarDireccion(viaje.destino)}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = TextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    SimpleDateFormat("dd MMM yyyy", Locale("es", "AR"))
                        .format(Date(viaje.fecha))
                        .replaceFirstChar { it.uppercaseChar() },
                    fontSize = 11.sp,
                    lineHeight = 12.sp,
                    color = TextHint
                )
            }
            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                val nf = NumberFormat.getNumberInstance(Locale("es", "AR")).apply { maximumFractionDigits = 0 }
                Text(
                    "AHORRASTE",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Verde,
                    letterSpacing = 0.5.sp
                )
                Text(
                    "+$${nf.format(viaje.ahorro)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Verde
                )
            }
        }
    }
}
