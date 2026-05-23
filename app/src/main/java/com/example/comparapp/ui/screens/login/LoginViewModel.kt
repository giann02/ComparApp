package com.example.comparapp.ui.screens.login

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comparapp.AppContainer
import com.example.comparapp.domain.repository.UsuarioRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    var estado by mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(email: String) {
        estado = estado.copy(email = email, error = null)
    }

    fun onPasswordChange(password: String) {
        estado = estado.copy(password = password, error = null)
    }

    fun login(onSuccess: () -> Unit) {
        val error = validarCampos(estado.email, estado.password)
        if (error != null) {
            estado = estado.copy(error = error)
            return
        }
        estado = estado.copy(isLoading = true, error = null)
        viewModelScope.launch {
            repository.login(estado.email, estado.password)
                .onSuccess { usuario ->
                    AppContainer.usuarioActual = usuario
                    estado = estado.copy(isLoading = false)
                    onSuccess()
                }
                .onFailure { throwable ->
                    estado = estado.copy(
                        isLoading = false,
                        error = throwable.message ?: "Error al iniciar sesión"
                    )
                }
        }
    }
}

private fun validarCampos(email: String, password: String): String? {
    if (email.isBlank()) return "El correo es requerido"
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) return "Ingrese un correo válido"
    if (password.isBlank()) return "La contraseña es requerida"
    return null
}
