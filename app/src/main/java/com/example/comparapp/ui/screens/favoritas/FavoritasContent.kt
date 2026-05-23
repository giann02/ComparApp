package com.example.comparapp.ui.screens.favoritas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RadioButtonChecked
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comparapp.domain.model.RutaFavorita
import com.example.comparapp.ui.theme.BackgroundColor
import com.example.comparapp.ui.theme.ComparBlue
import com.example.comparapp.ui.theme.DividerColor
import com.example.comparapp.ui.theme.SurfaceColor
import com.example.comparapp.ui.theme.TextHint
import com.example.comparapp.ui.theme.TextLabel
import com.example.comparapp.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritasContent(
    estado: FavoritasUiState,
    onBack: () -> Unit,
    onRutaClick: (origen: String, destino: String) -> Unit,
    onEliminar: (RutaFavorita) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        containerColor = BackgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Text("Mis Favoritas", fontWeight = FontWeight.Bold, color = ComparBlue)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = TextLabel
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        }
    ) { paddingValues ->
        if (estado.favoritas.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = TextHint,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text("No tenés favoritas guardadas", color = TextSecondary, fontSize = 15.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { Spacer(Modifier.height(8.dp)) }
                items(estado.favoritas, key = { it.id }) { ruta ->
                    TarjetaFavorita(
                        ruta = ruta,
                        onClick = { onRutaClick(ruta.origen, ruta.destino) },
                        onEliminar = { onEliminar(ruta) }
                    )
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

@Composable
private fun TarjetaFavorita(
    ruta: RutaFavorita,
    onClick: () -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(20.dp)
            ) {
                Icon(Icons.Default.RadioButtonChecked, null, tint = ComparBlue, modifier = Modifier.size(18.dp))
                Box(modifier = Modifier.width(2.dp).height(26.dp).background(DividerColor))
                Icon(Icons.Default.LocationOn, null, tint = TextSecondary, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("ORIGEN", fontSize = 9.sp, color = TextHint, letterSpacing = 0.5.sp)
                Text(ruta.origen, fontWeight = FontWeight.Bold, color = TextLabel, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(10.dp))
                Text("DESTINO", fontSize = 9.sp, color = TextHint, letterSpacing = 0.5.sp)
                Text(ruta.destino, fontWeight = FontWeight.Bold, color = TextLabel, fontSize = 14.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            IconButton(onClick = onEliminar, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Default.DeleteOutline, contentDescription = "Eliminar", tint = TextSecondary, modifier = Modifier.size(20.dp))
            }
        }
    }
}
