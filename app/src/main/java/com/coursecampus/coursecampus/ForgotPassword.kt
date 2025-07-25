package com.coursecampus.coursecampus

import android.widget.Toast
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coursecampus.coursecampus.ui.theme.DeepBlue

@Composable
fun ForgotPassword(navController: NavController) {
    val isDarkTheme = isSystemInDarkTheme()
    val textColor = if (isDarkTheme) Color.White else Color.Black
    val borderColor = if (isDarkTheme) Color.White else Color.Black
    val tintColor = if (isDarkTheme) Color.White else Color.Black
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isSubmitClicked by remember { mutableStateOf(false) }

    val isEmailError = email.isBlank() && isSubmitClicked
    val isOtpError = otp.isBlank() && isSubmitClicked
    val isPasswordError = password.isBlank() && isSubmitClicked
    val isConfirmPasswordError = (confirmPassword != password || confirmPassword.isBlank()) && isSubmitClicked

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
                verticalArrangement = Arrangement.Center // Align items to the top
            ) {
                // Title at the top without extra padding
                Text(
                    text = "Forgot Password",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.padding(top = 24.dp) // Optional: Add a little top padding if needed
                )

                // Card to wrap the input fields and button
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    elevation = CardDefaults.cardElevation(4.dp)

                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
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

                        OutlinedTextField(
                            value = otp,
                            onValueChange = { otp = it },
                            label = { Text("Enter OTP", color = if (isOtpError) Color.Red else textColor) },
                            isError = isOtpError,
                            leadingIcon = {
                                Icon (painter = painterResource(id = R.drawable.ic_otp),
                                    contentDescription = null,
                                    tint = textColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isOtpError) Color.Red else DeepBlue,
                                unfocusedBorderColor = if (isOtpError) Color.Red else borderColor,
                                cursorColor = DeepBlue,
                                focusedLabelColor = DeepBlue,
                                unfocusedLabelColor = textColor,
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            )
                        )

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("New Password", color = if (isPasswordError) Color.Red else textColor) },
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

                        OutlinedTextField(
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Re-enter Password", color = if (isConfirmPasswordError) Color.Red else textColor) },
                            isError = isConfirmPasswordError,
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
                                focusedBorderColor = if (isConfirmPasswordError) Color.Red else DeepBlue,
                                unfocusedBorderColor = if (isConfirmPasswordError) Color.Red else borderColor,
                                cursorColor = DeepBlue,
                                focusedLabelColor = DeepBlue,
                                unfocusedLabelColor = textColor,
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Password
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            isSubmitClicked = true
                            when {
                                email.isBlank() || !email.endsWith("@gmail.com") -> {
                                    Toast.makeText(context, "Enter a valid Gmail address", Toast.LENGTH_SHORT).show()
                                }
                                otp.isBlank() -> {
                                    Toast.makeText(context, "Enter the OTP", Toast.LENGTH_SHORT).show()
                                }
                                password.isBlank() -> {
                                    Toast.makeText(context, "Enter a new password", Toast.LENGTH_SHORT).show()
                                }
                                confirmPassword != password -> {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    Toast.makeText(context, "Password reset successful", Toast.LENGTH_LONG).show()
                                    navController.navigate("login")
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DeepBlue,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text("Reset Password")
                    }
                }
            }
        }
    }
}