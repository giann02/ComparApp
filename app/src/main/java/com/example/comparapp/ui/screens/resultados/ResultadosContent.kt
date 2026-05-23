package com.example.comparapp.ui.screens.resultados

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.NumberFormat
import java.util.Locale
import com.example.comparapp.ui.components.ComparAppLogo
import com.example.comparapp.ui.theme.BackgroundColor
import com.example.comparapp.ui.theme.ComparBlue
import com.example.comparapp.ui.theme.SurfaceColor
import com.example.comparapp.ui.theme.TextHint
import com.example.comparapp.ui.theme.TextLabel
import com.example.comparapp.ui.theme.TextSecondary

private val Verde = Color(0xFF16A34A)
private val VerdeFondo = Color(0xFFDCFCE7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultadosContent(
    estado: ResultadosUiState,
    onBack: () -> Unit,
    onReintentar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
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
        when {
            estado.isLoading -> PantallaLoading(Modifier.padding(paddingValues))
            estado.error != null -> PantallaError(
                mensaje = estado.error,
                onReintentar = onReintentar,
                onVolver = onBack,
                modifier = Modifier.padding(paddingValues)
            )
            else -> Contenido(estado = estado, modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
private fun PantallaLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = ComparBlue, modifier = Modifier.size(48.dp))
            Spacer(Modifier.height(16.dp))
            Text("Calculando precios...", color = TextSecondary, fontSize = 15.sp)
        }
    }
}

@Composable
private fun PantallaError(
    mensaje: String,
    onReintentar: () -> Unit,
    onVolver: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("😕", fontSize = 48.sp)
        Spacer(Modifier.height(16.dp))
        Text(mensaje, color = TextLabel, fontSize = 15.sp, textAlign = TextAlign.Center, lineHeight = 22.sp)
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onReintentar,
            colors = ButtonDefaults.buttonColors(containerColor = ComparBlue),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Reintentar")
        }
        Spacer(Modifier.height(12.dp))
        OutlinedButton(onClick = onVolver, shape = RoundedCornerShape(12.dp)) {
            Text("Volver", color = ComparBlue)
        }
    }
}

@Composable
private fun Contenido(
    estado: ResultadosUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(8.dp))

        // Encabezado
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Resultados",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = ComparBlue
                )
                Spacer(Modifier.height(4.dp))
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, null, tint = TextSecondary, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(
                            acortarDireccion(estado.origen),
                            fontSize = 13.sp,
                            color = TextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("→", fontSize = 13.sp, color = TextSecondary, modifier = Modifier.width(14.dp), textAlign = TextAlign.Center)
                        Spacer(Modifier.width(4.dp))
                        Text(
                            acortarDireccion(estado.destino),
                            fontSize = 13.sp,
                            color = TextSecondary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(top = 6.dp)
                    .border(1.dp, TextHint.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .padding(6.dp)
            ) {
                Icon(Icons.Default.Edit, null, tint = ComparBlue, modifier = Modifier.size(16.dp))
            }
        }

        Spacer(Modifier.height(24.dp))

        estado.proveedores.forEach { proveedor ->
            TarjetaProveedor(proveedor = proveedor)
            Spacer(Modifier.height(12.dp))
        }

        if (estado.ahorro > 0) {
            Spacer(Modifier.height(4.dp))
            CardAhorro(ahorro = estado.ahorro)
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun TarjetaProveedor(proveedor: ProveedorResultado) {
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
                    .size(52.dp)
                    .background(proveedor.color, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(proveedor.inicial, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
            }

            Spacer(Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(proveedor.nombre, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextLabel)
            }

            Column(horizontalAlignment = Alignment.End) {
                if (proveedor.esMejor) {
                    Box(
                        modifier = Modifier
                            .background(Verde, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text("MÁS BARATO", color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                    }
                    Spacer(Modifier.height(4.dp))
                }
                Text(
                    formatPrecio(proveedor.precio),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (proveedor.esMejor) Verde else TextLabel
                )
                Spacer(Modifier.height(6.dp))
                Button(
                    onClick = {},
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ComparBlue),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text("Seleccionar", fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
private fun CardAhorro(ahorro: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VerdeFondo),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "AHORRO POTENCIAL TOTAL",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Verde,
                    letterSpacing = 0.5.sp
                )
                Spacer(Modifier.height(4.dp))
                Text("Podés ahorrar en este viaje", fontSize = 13.sp, color = Verde.copy(alpha = 0.8f))
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(formatPrecio(ahorro), fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = ComparBlue)
                Text("AHORRO HOY", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Verde, letterSpacing = 0.5.sp)
            }
        }
    }
}

private fun acortarDireccion(direccion: String): String = direccion.substringBefore(",").trim()

private fun formatPrecio(precio: Int): String {
    val nf = NumberFormat.getNumberInstance(Locale("es", "AR"))
    nf.maximumFractionDigits = 0
    return "$${nf.format(precio)}"
}
