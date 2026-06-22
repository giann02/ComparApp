package com.example.comparapp.ui.screens.olvidarcontrasena

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comparapp.ui.components.ComparAppLogo
import com.example.comparapp.ui.theme.BackgroundColor
import com.example.comparapp.ui.theme.ComparBlue
import com.example.comparapp.ui.theme.DividerColor
import com.example.comparapp.ui.theme.ErrorColor
import com.example.comparapp.ui.theme.SurfaceColor
import com.example.comparapp.ui.theme.TextHint
import com.example.comparapp.ui.theme.TextLabel
import com.example.comparapp.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OlvidarContrasenaContent(
    estado: OlvidarContrasenaUiState,
    onEmailChange: (String) -> Unit,
    onNuevaPasswordChange: (String) -> Unit,
    onConfirmarPasswordChange: (String) -> Unit,
    onActualizar: () -> Unit,
    onBack: () -> Unit,
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Volver", tint = TextLabel)
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
                .navigationBarsPadding()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                "Restablecer contraseña",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = ComparBlue
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "Ingresá tu correo y establecé una nueva contraseña.",
                fontSize = 14.sp,
                color = TextSecondary
            )
            Spacer(Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    CampoLabel("CORREO ELECTRÓNICO")
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = estado.email,
                        onValueChange = onEmailChange,
                        placeholder = { Text("nombre@ejemplo.com", color = TextHint) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = coloresCampo()
                    )

                    Spacer(Modifier.height(16.dp))

                    CampoLabel("NUEVA CONTRASEÑA")
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = estado.nuevaPassword,
                        onValueChange = onNuevaPasswordChange,
                        placeholder = { Text("••••••••", color = TextHint) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = coloresCampo()
                    )

                    Spacer(Modifier.height(16.dp))

                    CampoLabel("CONFIRMAR CONTRASEÑA")
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = estado.confirmarPassword,
                        onValueChange = onConfirmarPasswordChange,
                        placeholder = { Text("••••••••", color = TextHint) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        colors = coloresCampo()
                    )

                    if (estado.error != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(estado.error, color = ErrorColor, fontSize = 12.sp)
                    }

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = onActualizar,
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ComparBlue),
                        enabled = !estado.isLoading
                    ) {
                        if (estado.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = SurfaceColor,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text(
                                "Actualizar contraseña",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CampoLabel(text: String) {
    Text(
        text = text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = TextLabel,
        letterSpacing = 0.8.sp
    )
}

@Composable
private fun coloresCampo() = OutlinedTextFieldDefaults.colors(
    unfocusedBorderColor = DividerColor,
    focusedBorderColor = ComparBlue,
    unfocusedContainerColor = SurfaceColor,
    focusedContainerColor = SurfaceColor
)
