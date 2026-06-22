package com.example.comparapp.ui.screens.facedetection

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FaceDetectionViewModel : ViewModel() {

    var estado by mutableStateOf(FaceDetectionUiState())
        private set

    private var confirmJob: Job? = null

    fun onPermissionGranted() {
        estado = estado.copy(hasPermission = true)
    }

    fun onFaceDetected(onConfirmed: () -> Unit) {
        if (estado.faceDetected) return
        confirmJob?.cancel()
        confirmJob = viewModelScope.launch {
            estado = estado.copy(faceDetected = true)
            delay(1200)
            estado = estado.copy(confirmed = true)
            delay(400)
            onConfirmed()
        }
    }

    fun onNoFace() {
        if (!estado.faceDetected) {
            confirmJob?.cancel()
        }
    }
}
