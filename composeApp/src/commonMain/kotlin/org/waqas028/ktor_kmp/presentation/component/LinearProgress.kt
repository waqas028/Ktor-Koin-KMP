package org.waqas028.ktor_kmp.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun LinearProgress(modifier: Modifier = Modifier){
    LinearProgressIndicator(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        backgroundColor = MaterialTheme.colors.secondary,
        strokeCap = StrokeCap.Round
    )
}