package com.example.comparapp.ui.screens.facedetection

import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class FaceAnalyzer(
    private val onFaceDetected: () -> Unit,
    private val onNoFace: () -> Unit
) : ImageAnalysis.Analyzer, AutoCloseable {

    private val detector = FaceDetection.getClient(
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .setMinFaceSize(0.25f)
            .build()
    )

    // Procesa un frame cada 300ms para no saturar el hilo
    private var lastAnalyzedTime = 0L

    override fun close() {
        detector.close()
    }

    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        val now = System.currentTimeMillis()
        if (now - lastAnalyzedTime < 300) {
            imageProxy.close()
            return
        }
        lastAnalyzedTime = now

        val mediaImage = imageProxy.image ?: run {
            imageProxy.close()
            return
        }
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) onFaceDetected() else onNoFace()
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}
