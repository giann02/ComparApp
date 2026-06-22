package com.example.comparapp.ui.screens.facedetection

data class FaceDetectionUiState(
    val hasPermission: Boolean = false,
    val faceDetected: Boolean = false,
    val confirmed: Boolean = false
)
