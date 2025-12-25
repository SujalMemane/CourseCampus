package com.coursecampus.coursecampus.feature.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.coursecampus.coursecampus.R
import com.coursecampus.coursecampus.core.ui.theme.DeepBlue

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val borderColor = if (isDarkTheme) Color.White else Color.Black
    val tintColor = if (isDarkTheme) Color.White else Color.Black
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isSubmitClicked by remember { mutableStateOf(false) }

    val isEmailError = (email.isBlank() || !email.contains("@")) && isSubmitClicked
    val isPasswordError = password.isBlank() && isSubmitClicked

    // Handle auth state changes
    LaunchedEffect(authState) {
        if (authState.isLoggedIn) {
            Toast.makeText(context, "Logged in successfully ✅", Toast.LENGTH_SHORT).show()
            navController.navigate("mainScreen") {
                popUpTo("login") { inclusive = true }
            }
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
        Box(modifier = Modifier
            .fillMaxSize()
            .imePadding()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_chat),
                    contentDescription = "App Icon",
                    modifier = Modifier.size(100.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Card to wrap the input fields and buttons
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    elevation = CardDefaults.cardElevation(4.dp)

                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
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

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password", color = if (isPasswordError) Color.Red else textColor) },
                            isError = isPasswordError,
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = textColor)
                            },
                            trailingIcon = {
                                val eyeIcon = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        painter = painterResource(id = eyeIcon),
                                        contentDescription = "Toggle Password Visibility",
                                        tint = tintColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isPasswordError) Color.Red else DeepBlue,
                                unfocusedBorderColor = if (isPasswordError) Color.Red else borderColor,
                                cursorColor = DeepBlue,
                                focusedLabelColor = DeepBlue,
                                unfocusedLabelColor = textColor,
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            )
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = "Forgot Password?",
                                color = DeepBlue,
                                fontSize = 14.sp,
                                modifier = Modifier.clickable {
                                    navController.navigate("forgotPassword")
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
                                isSubmitClicked = true
                                when {
                                    email.isBlank() || !email.contains("@") -> {
                                        Toast.makeText(context, "Enter a valid email address", Toast.LENGTH_SHORT).show()
                                    }
                                    password.isBlank() -> {
                                        Toast.makeText(context, "Enter your password", Toast.LENGTH_SHORT).show()
                                    }
                                    else -> {
                                        viewModel.signIn(email, password)
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
                                Text("Login")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = { /* Handle Google sign-in */ },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_google),
                                contentDescription = "Google",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sign in with Google", color = DeepBlue)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Text("Don't have an account?", color = textColor)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Create Account",
                        color = DeepBlue,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.navigate("register")
                        }
                    )
                }
            }

            Text(
                text = "Skip",
                color = DeepBlue,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(32.dp)
                    .clickable {
                        navController.navigate("mainScreen")
                    }
            )
        }
    }
}

@Preview
@Composable
private fun prevv() {
    LoginScreen(navController = rememberNavController())
}