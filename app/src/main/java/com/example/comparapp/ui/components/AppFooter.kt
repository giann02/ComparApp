package com.example.comparapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comparapp.ui.theme.TextHint

@Composable
fun AppFooter(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "© 2024 COMPARAPP. TODOS LOS DERECHOS RESERVADOS.",
            fontSize = 10.sp,
            color = TextHint,
            textAlign = TextAlign.Center
        )
        Row(horizontalArrangement = Arrangement.Center) {
            FooterLink("PRIVACIDAD")
            Spacer(Modifier.width(12.dp))
            FooterLink("SEGURIDAD")
            Spacer(Modifier.width(12.dp))
            FooterLink("SOPORTE")
        }
    }
}

@Composable
private fun FooterLink(text: String) {
    Text(text = text, fontSize = 10.sp, color = TextHint)
}
