package com.coursecampus.coursecampus.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coursecampus.coursecampus.R
import com.coursecampus.coursecampus.core.ui.theme.CourseCampusTheme
import kotlinx.coroutines.delay

/**
 * Splash Screen - Entry point of the app
 * Simulates auth check and decides start destination
 */
@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    // Simulate auth check
    LaunchedEffect(Unit) {
        delay(2000) // Show splash for 2 seconds
        
        // TODO: Replace with actual auth check
        val isUserLoggedIn = checkAuthStatus()
        
        if (isUserLoggedIn) {
            onNavigateToHome()
        } else {
            onNavigateToLogin()
        }
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // App Logo
            Image(
                painter = painterResource(id = R.drawable.ic_chat),
                contentDescription = "Course Campus Logo",
                modifier = Modifier.size(120.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // App Name
            Text(
                text = "Course Campus",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Learn. Grow. Succeed.",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Loading Indicator
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * Simulated auth check - Replace with actual implementation
 */
private suspend fun checkAuthStatus(): Boolean {
    // Simulate network call
    delay(500)
    
    // TODO: Implement actual auth check
    // Check if user is logged in via Firebase Auth, SharedPreferences, etc.
    return false // For now, always navigate to login
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {
    CourseCampusTheme {
        SplashScreen(
            onNavigateToLogin = {},
            onNavigateToHome = {}
        )
    }
}