package com.example.comparapp.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdd
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Savings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comparapp.domain.model.RutaFavorita
import com.example.comparapp.ui.components.ComparAppLogo
import com.example.comparapp.ui.theme.BackgroundColor
import com.example.comparapp.ui.theme.ComparBlue
import com.example.comparapp.ui.theme.ComparBlueLight
import com.example.comparapp.ui.theme.DividerColor
import com.example.comparapp.ui.theme.SurfaceColor
import com.example.comparapp.ui.theme.TextHint
import com.example.comparapp.ui.theme.TextLabel
import com.example.comparapp.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(
    estado: MainUiState,
    onOrigenChange: (String) -> Unit,
    onDestinoChange: (String) -> Unit,
    onSugerenciaOrigenClick: (String) -> Unit,
    onSugerenciaDestinoClick: (String) -> Unit,
    onSwapClick: () -> Unit,
    onCompararClick: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onRutaFavoritaClick: (RutaFavorita) -> Unit,
    onEliminarFavorita: (RutaFavorita) -> Unit,
    onGuardarFavorita: () -> Boolean,
    onVerTodas: () -> Unit,
    onCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        containerColor = BackgroundColor,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { ComparAppLogo() },
                actions = {
                    var menuExpanded by remember { mutableStateOf(false) }
                    val inicial = estado.nombreUsuario.firstOrNull()?.uppercaseChar()?.toString() ?: "?"
                    IconButton(onClick = { menuExpanded = true }) {
                        Surface(
                            shape = CircleShape,
                            color = ComparBlue,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = inicial,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .widthIn(min = 200.dp)
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = ComparBlue,
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = inicial,
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = estado.nombreUsuario,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp,
                                color = TextLabel
                            )
                            Text(
                                text = estado.emailUsuario,
                                fontSize = 12.sp,
                                color = TextSecondary
                            )
                        }
                        HorizontalDivider()
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Cerrar sesión",
                                    color = Color(0xFFDC2626),
                                    fontWeight = FontWeight.Medium
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Logout,
                                    contentDescription = null,
                                    tint = Color(0xFFDC2626)
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                onCerrarSesion()
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundColor)
            )
        },
        bottomBar = {
            NavigationBar(containerColor = SurfaceColor) {
                NavigationBarItem(
                    selected = estado.selectedTab == 0,
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
                    selected = estado.selectedTab == 1,
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
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                text = "Ruta y Tarifa",
                style = MaterialTheme.typography.headlineMedium,
                color = ComparBlue,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            TarjetaRuta(
                origen = estado.origen,
                destino = estado.destino,
                onOrigenChange = onOrigenChange,
                onDestinoChange = onDestinoChange,
                onSwapClick = onSwapClick
            )

            if (estado.sugerenciasOrigen.isNotEmpty()) {
                TarjetaSugerencias(
                    sugerencias = estado.sugerenciasOrigen,
                    onSugerenciaClick = onSugerenciaOrigenClick
                )
            } else if (estado.sugerenciasDestino.isNotEmpty()) {
                TarjetaSugerencias(
                    sugerencias = estado.sugerenciasDestino,
                    onSugerenciaClick = onSugerenciaDestinoClick
                )
            }

            if (estado.origen.isNotBlank() && estado.destino.isNotBlank()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            val guardado = onGuardarFavorita()
                            scope.launch {
                                val mensaje = if (guardado)
                                    "Ruta guardada en favoritas"
                                else
                                    "Esta ruta ya está en tus favoritas"
                                snackbarHostState.showSnackbar(mensaje)
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.BookmarkAdd,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = ComparBlue
                        )
                        Spacer(Modifier.width(6.dp))
                        Text("Guardar como favorita", color = ComparBlue, fontSize = 13.sp)
                    }
                }
            } else {
                Spacer(Modifier.height(16.dp))
            }

            Button(
                onClick = onCompararClick,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ComparBlue)
            ) {
                Text("Comparar →", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "RUTAS FAVORITAS",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextSecondary,
                    letterSpacing = 1.sp
                )
                if (estado.rutasFavoritas.isNotEmpty()) {
                    TextButton(onClick = onVerTodas, contentPadding = PaddingValues(0.dp)) {
                        Text("Ver todas", color = ComparBlue, fontSize = 13.sp)
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            if (estado.rutasFavoritas.isEmpty()) {
                Text(
                    "Guardá rutas frecuentes para acceder rápido",
                    fontSize = 13.sp,
                    color = TextHint,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            } else {
                estado.rutasFavoritas.take(2).forEachIndexed { index, ruta ->
                    TarjetaRutaFavorita(
                        ruta = ruta,
                        onClick = { onRutaFavoritaClick(ruta) },
                        onEliminar = { onEliminarFavorita(ruta) }
                    )
                    if (index < minOf(estado.rutasFavoritas.size, 2) - 1) {
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun TarjetaRuta(
    origen: String,
    destino: String,
    onOrigenChange: (String) -> Unit,
    onDestinoChange: (String) -> Unit,
    onSwapClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.RadioButtonUnchecked,
                        contentDescription = null,
                        tint = TextSecondary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    BasicTextField(
                        value = origen,
                        onValueChange = onOrigenChange,
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = TextLabel, fontSize = 15.sp),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (origen.isEmpty()) {
                                Text("Punto de partida", color = TextHint, fontSize = 15.sp)
                            }
                            innerTextField()
                        }
                    )
                }

                HorizontalDivider(
                    modifier = Modifier.padding(start = 32.dp, end = 56.dp),
                    color = DividerColor
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = ComparBlue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    BasicTextField(
                        value = destino,
                        onValueChange = onDestinoChange,
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(color = TextLabel, fontSize = 15.sp),
                        singleLine = true,
                        decorationBox = { innerTextField ->
                            if (destino.isEmpty()) {
                                Text("Destino", color = TextHint, fontSize = 15.sp)
                            }
                            innerTextField()
                        }
                    )
                }
            }

            IconButton(
                onClick = onSwapClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
                    .size(36.dp)
                    .background(BackgroundColor, CircleShape)
            ) {
                Icon(
                    Icons.Default.SwapVert,
                    contentDescription = "Intercambiar",
                    tint = TextSecondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
private fun TarjetaSugerencias(
    sugerencias: List<String>,
    onSugerenciaClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        sugerencias.forEachIndexed { index, sugerencia ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSugerenciaClick(sugerencia) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text(
                    text = sugerencia,
                    fontSize = 14.sp,
                    color = TextLabel,
                    maxLines = 1,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                )
            }
            if (index < sugerencias.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(start = 46.dp), color = DividerColor)
            }
        }
    }
}

@Composable
private fun TarjetaRutaFavorita(
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
                Text(ruta.origen, fontWeight = FontWeight.Bold, color = TextLabel, fontSize = 14.sp, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
                Spacer(Modifier.height(10.dp))
                Text("DESTINO", fontSize = 9.sp, color = TextHint, letterSpacing = 0.5.sp)
                Text(ruta.destino, fontWeight = FontWeight.Bold, color = TextLabel, fontSize = 14.sp, maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
            }
        }
    }
}
