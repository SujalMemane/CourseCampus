package com.coursecampus.coursecampus.ui.screens.auth.verify

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coursecampus.coursecampus.R
import com.coursecampus.coursecampus.core.ui.theme.CourseCampusTheme
import kotlinx.coroutines.delay

/**
 * Email Verification Screen
 * Shows after successful signup, guides user through email verification
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailVerificationScreen(
    onVerificationSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var isResending by remember { mutableStateOf(false) }
    var resendCooldown by remember { mutableStateOf(0) }
    var isVerifying by remember { mutableStateOf(false) }
    var showSuccessState by remember { mutableStateOf(false) }
    
    // Resend cooldown timer
    LaunchedEffect(resendCooldown) {
        if (resendCooldown > 0) {
            delay(1000)
            resendCooldown--
        }
    }
    
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top App Bar
            TopAppBar(
                title = { Text("Verify Email") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
            
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (showSuccessState) {
                    // Success State
                    SuccessStateContent(onVerificationSuccess)
                } else {
                    // Verification State
                    VerificationStateContent(
                        isResending = isResending,
                        resendCooldown = resendCooldown,
                        isVerifying = isVerifying,
                        onResendEmail = {
                            isResending = true
                            resendCooldown = 60
                            // Simulate resend
                            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                                delay(1000)
                                isResending = false
                            }
                        },
                        onVerifyEmail = {
                            isVerifying = true
                            // Simulate verification
                            kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                                delay(2000)
                                isVerifying = false
                                showSuccessState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun VerificationStateContent(
    isResending: Boolean,
    resendCooldown: Int,
    isVerifying: Boolean,
    onResendEmail: () -> Unit,
    onVerifyEmail: () -> Unit
) {
    // Email Illustration
    Card(
        modifier = Modifier.size(120.dp),
        shape = RoundedCornerShape(60.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                modifier = Modifier.size(60.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
    
    Spacer(modifier = Modifier.height(32.dp))
    
    Text(
        text = "Check Your Email",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    Text(
        text = "We've sent a verification link to your email address. Please check your inbox and click the link to verify your account.",
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        textAlign = TextAlign.Center,
        lineHeight = 24.sp
    )
    
    Spacer(modifier = Modifier.height(32.dp))
    
    // Action Buttons
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Verify Button
        Button(
            onClick = onVerifyEmail,
            enabled = !isVerifying,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isVerifying) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Verifying...")
            } else {
                Text("I've Verified My Email", fontSize = 16.sp)
            }
        }
        
        // Resend Button
        OutlinedButton(
            onClick = onResendEmail,
            enabled = !isResending && resendCooldown == 0,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            if (isResending) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Sending...")
            } else if (resendCooldown > 0) {
                Text("Resend in ${resendCooldown}s")
            } else {
                Text("Resend Email")
            }
        }
    }
    
    Spacer(modifier = Modifier.height(24.dp))
    
    // Help Text
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Didn't receive the email?",
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "• Check your spam/junk folder\n• Make sure you entered the correct email\n• Try resending the verification email",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                lineHeight = 20.sp
            )
        }
    }
}

@Composable
private fun SuccessStateContent(
    onVerificationSuccess: () -> Unit
) {
    // Success Illustration
    Card(
        modifier = Modifier.size(120.dp),
        shape = RoundedCornerShape(60.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_chat), // Use a checkmark icon if available
                contentDescription = "Success",
                modifier = Modifier.size(60.dp)
            )
        }
    }
    
    Spacer(modifier = Modifier.height(32.dp))
    
    Text(
        text = "Email Verified!",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF4CAF50),
        textAlign = TextAlign.Center
    )
    
    Spacer(modifier = Modifier.height(16.dp))
    
    Text(
        text = "Great! Your email has been successfully verified. You can now access all features of Course Campus.",
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        textAlign = TextAlign.Center,
        lineHeight = 24.sp
    )
    
    Spacer(modifier = Modifier.height(32.dp))
    
    // Continue Button
    Button(
        onClick = onVerificationSuccess,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text("Continue to App", fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
private fun EmailVerificationScreenPreview() {
    CourseCampusTheme {
        EmailVerificationScreen(
            onVerificationSuccess = {},
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmailVerificationSuccessPreview() {
    CourseCampusTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SuccessStateContent(onVerificationSuccess = {})
            }
        }
    }
}