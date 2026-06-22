package com.example.comparapp.ui.screens.misahorros

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comparapp.domain.model.Ahorro
import com.example.comparapp.ui.components.ComparAppLogo
import com.example.comparapp.ui.theme.BackgroundColor
import com.example.comparapp.ui.theme.ComparBlue
import com.example.comparapp.ui.theme.ComparBlueLight
import com.example.comparapp.ui.theme.SurfaceColor
import com.example.comparapp.ui.theme.TextHint
import com.example.comparapp.ui.theme.TextLabel
import com.example.comparapp.ui.theme.TextSecondary
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val Verde = Color(0xFF16A34A)
private val VerdeFondo = Color(0xFFDCFCE7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisAhorrosContent(
    estado: MisAhorrosUiState,
    onTabSelected: (Int) -> Unit,
    onVerHistorial: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = BackgroundColor,
        topBar = {
            TopAppBar(
                title = { ComparAppLogo() },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = SurfaceColor) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onTabSelected(0) },
                    icon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                    label = { Text("BUSCAR", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ComparBlue,
                        selectedTextColor = ComparBlue,
                        indicatorColor = ComparBlueLight
                    )
                )
                NavigationBarItem(
                    selected = true,
                    onClick = { onTabSelected(1) },
                    icon = { Icon(Icons.Outlined.Savings, contentDescription = null) },
                    label = { Text("MIS AHORROS", fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = ComparBlue,
                        selectedTextColor = ComparBlue,
                        indicatorColor = ComparBlueLight
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            SeccionAhorroTotal(
                ahorroTotal = estado.ahorroTotal,
                tieneViajes = estado.ultimosViajes.isNotEmpty()
            )

            Spacer(Modifier.height(32.dp))

            SeccionActividadReciente(ultimosViajes = estado.ultimosViajes, onVerTodo = onVerHistorial)

            Spacer(Modifier.height(20.dp))

            if (estado.ahorroPromedio > 0) {
                CardAhorroPromedio(ahorroPromedio = estado.ahorroPromedio)
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SeccionAhorroTotal(ahorroTotal: Int, tieneViajes: Boolean) {
    Text(
        "AHORRO TOTAL ACUMULADO",
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = TextSecondary,
        letterSpacing = 1.sp
    )
    Spacer(Modifier.height(8.dp))
    Text(
        formatPrecio(ahorroTotal),
        fontSize = 44.sp,
        fontWeight = FontWeight.ExtraBold,
        color = ComparBlue
    )
    Spacer(Modifier.height(12.dp))
    if (tieneViajes) {
        Box(
            modifier = Modifier
                .background(VerdeFondo, RoundedCornerShape(20.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Verde, CircleShape)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    "Top 2% de Ahorradores",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Verde
                )
            }
        }
    }
}

@Composable
private fun SeccionActividadReciente(ultimosViajes: List<Ahorro>, onVerTodo: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "Actividad Reciente",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextLabel
        )
        TextButton(onClick = onVerTodo) {
            Text("Ver todo", color = ComparBlue, fontSize = 13.sp)
        }
    }

    Spacer(Modifier.height(8.dp))

    if (ultimosViajes.isEmpty()) {
        Text(
            "Todavía no comparaste ningún viaje",
            fontSize = 9.sp,
            color = TextHint,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    } else {
        ultimosViajes.forEach { viaje ->
            TarjetaViaje(viaje = viaje)
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Composable
private fun TarjetaViaje(viaje: Ahorro) {
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
                    formatFecha(viaje.fecha),
                    fontSize = 11.sp,
                    lineHeight = 12.sp,
                    color = TextHint
                )
            }
            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    "AHORRASTE",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Verde,
                    letterSpacing = 0.5.sp
                )
                Text(
                    "+${formatPrecio(viaje.ahorro)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Verde
                )
            }
        }
    }
}

@Composable
private fun CardAhorroPromedio(ahorroPromedio: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ComparBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Ahorro Promedio por Viaje",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    "Promediás ${formatPrecio(ahorroPromedio.toInt())} de ahorro por viaje con ComparApp.",
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    lineHeight = 17.sp
                )
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White.copy(alpha = 0.15f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.TrendingUp,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

private fun acortarDireccion(direccion: String): String =
    direccion.substringBefore(",").trim().split(" ").take(3).joinToString(" ")

private fun formatPrecio(precio: Int): String {
    val nf = NumberFormat.getNumberInstance(Locale("es", "AR"))
    nf.maximumFractionDigits = 0
    return "$${nf.format(precio)}"
}

private fun formatFecha(fecha: Long): String {
    val sdf = SimpleDateFormat("dd MMM", Locale("es", "AR"))
    return sdf.format(Date(fecha)).replaceFirstChar { it.uppercaseChar() }
}
