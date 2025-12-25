package com.coursecampus.coursecampus.feature.auth

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coursecampus.coursecampus.core.ui.theme.DeepBlue

@Composable
fun ForgotPassword(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val borderColor = if (isDarkTheme) Color.White else Color.Black
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isSubmitClicked by remember { mutableStateOf(false) }

    val isEmailError = (email.isBlank() || !email.contains("@")) && isSubmitClicked

    // Handle auth state changes
    LaunchedEffect(authState) {
        if (authState.passwordResetSent) {
            Toast.makeText(context, "Password reset email sent! Check your inbox.", Toast.LENGTH_LONG).show()
            viewModel.clearPasswordResetState()
            navController.navigate("login")
        }
        
        authState.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize().imePadding()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Forgot Password",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.padding(top = 24.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Enter your email address and we'll send you a link to reset your password.",
                            color = textColor.copy(alpha = 0.7f),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email", color = if (isEmailError) Color.Red else textColor) },
                            isError = isEmailError,
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = textColor)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isEmailError) Color.Red else DeepBlue,
                                unfocusedBorderColor = if (isEmailError) Color.Red else borderColor,
                                cursorColor = DeepBlue,
                                focusedLabelColor = DeepBlue,
                                unfocusedLabelColor = textColor,
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Email
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                isSubmitClicked = true
                                when {
                                    email.isBlank() || !email.contains("@") -> {
                                        Toast.makeText(context, "Enter a valid email address", Toast.LENGTH_SHORT).show()
                                    }
                                    else -> {
                                        viewModel.resetPassword(email)
                                    }
                                }
                            },
                            enabled = !authState.isLoading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DeepBlue,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            if (authState.isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            } else {
                                Text("Send Reset Email")
                            }
                        }
                    }
                }
            }
        }
    }
}