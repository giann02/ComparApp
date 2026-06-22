package com.example.comparapp.ui.screens.register

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comparapp.AppContainer
import com.example.comparapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    var estado by mutableStateOf(RegisterUiState())
        private set

    fun onNombreChange(nombre: String) { estado = estado.copy(nombre = nombre, error = null) }
    fun onEmailChange(email: String) { estado = estado.copy(email = email, error = null) }
    fun onPasswordChange(password: String) { estado = estado.copy(password = password, error = null) }
    fun onConfirmPasswordChange(confirm: String) { estado = estado.copy(confirmPassword = confirm, error = null) }

    fun register(onSuccess: () -> Unit) {
        val error = validarCampos(estado)
        if (error != null) {
            estado = estado.copy(error = error)
            return
        }
        estado = estado.copy(isLoading = true, error = null)
        viewModelScope.launch {
            repository.registrar(estado.nombre, estado.email, estado.password)
                .onSuccess { usuario ->
                    AppContainer.setUsuario(usuario)
                    estado = estado.copy(isLoading = false)
                    onSuccess()
                }
                .onFailure { throwable ->
                    estado = estado.copy(
                        isLoading = false,
                        error = throwable.message ?: "Error al registrarse"
                    )
                }
        }
    }
}

private fun validarCampos(estado: RegisterUiState): String? {
    if (estado.nombre.isBlank()) return "El nombre es requerido"
    if (estado.email.isBlank()) return "El correo es requerido"
    if (!Patterns.EMAIL_ADDRESS.matcher(estado.email).matches()) return "Ingrese un correo válido"
    if (estado.password.length < 6) return "La contraseña debe tener al menos 6 caracteres"
    if (estado.password != estado.confirmPassword) return "Las contraseñas no coinciden"
    return null
}
