package com.example.comparapp.ui.screens.facedetection

import android.annotation.SuppressLint
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors

@SuppressLint("RestrictedApi")
@Composable
fun FaceDetectionContent(
    estado: FaceDetectionUiState,
    onFaceDetected: () -> Unit,
    onNoFace: () -> Unit,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (estado.hasPermission) {
            CameraPreviewView(
                onFaceDetected = onFaceDetected,
                onNoFace = onNoFace
            )
        }

        // Capa oscura semitransparente encima de la cámara
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.45f))
        )

        // Botón volver
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver",
                tint = Color.White
            )
        }

        // Texto superior
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 16.dp, start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Verificación de presencia",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Mirá a la cámara",
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }

        // Feedback inferior
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp, start = 32.dp, end = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DetectionStatusText(estado = estado)
        }
    }
}


@Composable
private fun DetectionStatusText(estado: FaceDetectionUiState) {
    val textColor by animateColorAsState(
        targetValue = when {
            estado.confirmed    -> Color(0xFF00C853)
            estado.faceDetected -> Color(0xFF69F0AE)
            else                -> Color.White.copy(alpha = 0.6f)
        },
        animationSpec = tween(500),
        label = "textColor"
    )

    val iconScale by animateFloatAsState(
        targetValue = if (estado.faceDetected) 1f else 0f,
        animationSpec = spring(dampingRatio = 0.4f, stiffness = 300f),
        label = "iconScale"
    )

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Ícono animado
        AnimatedVisibility(
            visible = estado.faceDetected,
            enter = scaleIn(spring(dampingRatio = 0.4f, stiffness = 300f)) + fadeIn()
        ) {
            Icon(
                imageVector = if (estado.confirmed) Icons.Default.CheckCircle else Icons.Default.Face,
                contentDescription = null,
                tint = if (estado.confirmed) Color(0xFF00C853) else Color(0xFF69F0AE),
                modifier = Modifier
                    .size(64.dp)
                    .scale(iconScale)
            )
        }

        if (!estado.faceDetected) {
            Spacer(modifier = Modifier.height(64.dp))
        } else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = when {
                estado.confirmed    -> "Identidad confirmada"
                estado.faceDetected -> "Rostro detectado..."
                else                -> "Buscando rostro..."
            },
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = textColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CameraPreviewView(
    onFaceDetected: () -> Unit,
    onNoFace: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }
    val analysisExecutor = remember { Executors.newSingleThreadExecutor() }

    DisposableEffect(lifecycleOwner) {
        var cameraProvider: ProcessCameraProvider? = null
        val faceAnalyzer = FaceAnalyzer(onFaceDetected, onNoFace)
        val future = ProcessCameraProvider.getInstance(context)

        future.addListener({
            cameraProvider = future.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                .build()
                .also {
                    it.setAnalyzer(analysisExecutor, faceAnalyzer)
                }

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build()

            try {
                cameraProvider?.unbindAll()
                cameraProvider?.bindToLifecycle(
                    lifecycleOwner, cameraSelector, preview, imageAnalysis
                )
            } catch (e: Exception) {
                android.util.Log.e("FaceDetection", "Error al iniciar cámara", e)
            }
        }, ContextCompat.getMainExecutor(context))

        onDispose {
            cameraProvider?.unbindAll()
            analysisExecutor.shutdown()
            faceAnalyzer.close()
        }
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )
}
