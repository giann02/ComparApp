package com.example.comparapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.comparapp.ui.theme.ComparBlue

@Composable
fun ComparAppLogo(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = ComparBlue,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = "ComparApp",
            style = MaterialTheme.typography.titleLarge,
            color = ComparBlue,
            fontWeight = FontWeight.Bold
        )
    }
}
