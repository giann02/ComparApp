package com.example.comparapp.ui.screens.olvidarcontrasena

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comparapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.launch

class OlvidarContrasenaViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    var estado by mutableStateOf(OlvidarContrasenaUiState())
        private set

    fun onEmailChange(v: String)           { estado = estado.copy(email = v, error = null) }
    fun onNuevaPasswordChange(v: String)   { estado = estado.copy(nuevaPassword = v, error = null) }
    fun onConfirmarPasswordChange(v: String) { estado = estado.copy(confirmarPassword = v, error = null) }

    fun onActualizar(onExito: () -> Unit) {
        val error = validar(estado)
        if (error != null) { estado = estado.copy(error = error); return }
        estado = estado.copy(isLoading = true, error = null)
        viewModelScope.launch {
            repository.actualizarPassword(estado.email, estado.nuevaPassword)
                .onSuccess {
                    estado = estado.copy(isLoading = false, exito = true)
                    onExito()
                }
                .onFailure {
                    estado = estado.copy(isLoading = false, error = it.message ?: "Error al actualizar")
                }
        }
    }
}

private fun validar(s: OlvidarContrasenaUiState): String? {
    if (s.email.isBlank()) return "El correo es requerido"
    if (!Patterns.EMAIL_ADDRESS.matcher(s.email).matches()) return "Ingresá un correo válido"
    if (s.nuevaPassword.length < 6) return "La nueva contraseña debe tener al menos 6 caracteres"
    if (s.nuevaPassword != s.confirmarPassword) return "Las contraseñas no coinciden"
    return null
}
