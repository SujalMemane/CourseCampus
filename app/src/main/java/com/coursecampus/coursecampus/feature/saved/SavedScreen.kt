package com.coursecampus.coursecampus.feature.saved

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SavedScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF000000)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Corrected from HorizontalAlignment to horizontalAlignment
    ) {
        Text(
            text = "Saved Screen",
            fontSize = 48.sp, // Corrected from FontSzie to fontSize
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}