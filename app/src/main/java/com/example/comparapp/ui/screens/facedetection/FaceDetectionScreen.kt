package com.example.comparapp.ui.screens.facedetection

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FaceDetectionScreen(
    onConfirmed: () -> Unit,
    onBack: () -> Unit,
    viewModel: FaceDetectionViewModel = viewModel()
) {
    val context = LocalContext.current
    val estado = viewModel.estado

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) viewModel.onPermissionGranted()
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.onPermissionGranted()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    FaceDetectionContent(
        estado = estado,
        onFaceDetected = { viewModel.onFaceDetected(onConfirmed) },
        onNoFace = viewModel::onNoFace,
        onBack = onBack
    )
}
