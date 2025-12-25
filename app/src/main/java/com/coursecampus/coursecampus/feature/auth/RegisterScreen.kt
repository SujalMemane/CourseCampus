package com.coursecampus.coursecampus.feature.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.coursecampus.coursecampus.R
import com.coursecampus.coursecampus.core.ui.theme.DeepBlue
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
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
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var dateOfBirth by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isSubmitClicked by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Date picker state
    val datePickerState = rememberDatePickerState()

    // Validation states
    val isEmailError = (email.isBlank() || !email.contains("@")) && isSubmitClicked
    val isUsernameError = username.isBlank() && isSubmitClicked
    val isPasswordError = password.isBlank() && isSubmitClicked
    val isConfirmPasswordError = (confirmPassword != password || confirmPassword.isBlank()) && isSubmitClicked
    val isDobError = dateOfBirth.isBlank() && isSubmitClicked

    // Handle auth state changes
    LaunchedEffect(authState) {
        if (authState.isLoggedIn) {
            Toast.makeText(context, "Account created successfully!", Toast.LENGTH_LONG).show()
            navController.navigate("mainScreen") {
                popUpTo("login") { inclusive = true }
            }
        }
        
        authState.error?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
            viewModel.clearError()
        }
    }

    // Date picker dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val calendar = Calendar.getInstance()
                            calendar.timeInMillis = millis
                            val day = calendar.get(Calendar.DAY_OF_MONTH)
                            val month = calendar.get(Calendar.MONTH) + 1
                            val year = calendar.get(Calendar.YEAR)
                            dateOfBirth = String.format("%02d/%02d/%04d", day, month, year)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            
            Icon(
                painter = painterResource(R.drawable.ic_chat),
                contentDescription = "App Icon",
                modifier = Modifier.size(80.dp),
                tint = Color.Unspecified
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Create Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card to wrap the input fields and buttons
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
                    // Email Field
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

                    // Username Field
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username", color = if (isUsernameError) Color.Red else textColor) },
                        isError = isUsernameError,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = textColor)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isUsernameError) Color.Red else DeepBlue,
                            unfocusedBorderColor = if (isUsernameError) Color.Red else borderColor,
                            cursorColor = DeepBlue,
                            focusedLabelColor = DeepBlue,
                            unfocusedLabelColor = textColor,
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text
                        )
                    )

                    // Date of Birth Field with Date Picker
                    Box {
                        OutlinedTextField(
                            value = dateOfBirth,
                            onValueChange = { },
                            readOnly = true,
                            label = { Text("Date of Birth", color = if (isDobError) Color.Red else textColor) },
                            isError = isDobError,
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.DateRange, contentDescription = null, tint = textColor)
                            },
                            placeholder = { Text("Select your date of birth", color = textColor.copy(alpha = 0.6f)) },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (isDobError) Color.Red else DeepBlue,
                                unfocusedBorderColor = if (isDobError) Color.Red else borderColor,
                                cursorColor = DeepBlue,
                                focusedLabelColor = DeepBlue,
                                unfocusedLabelColor = textColor,
                            )
                        )
                        // Invisible clickable overlay
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { showDatePicker = true }
                        )
                    }

                    // Password Field
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

                    // Confirm Password Field
                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm Password", color = if (isConfirmPasswordError) Color.Red else textColor) },
                        isError = isConfirmPasswordError,
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = textColor)
                        },
                        trailingIcon = {
                            val eyeIcon = if (confirmPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    painter = painterResource(id = eyeIcon),
                                    contentDescription = "Toggle Confirm Password Visibility",
                                    tint = tintColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        },
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
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

                    Spacer(modifier = Modifier.height(8.dp))

                    // Register Button
                    Button(
                        onClick = {
                            isSubmitClicked = true
                            when {
                                email.isBlank() || !email.contains("@") -> {
                                    Toast.makeText(context, "Enter a valid email address", Toast.LENGTH_SHORT).show()
                                }
                                username.isBlank() -> {
                                    Toast.makeText(context, "Enter a username", Toast.LENGTH_SHORT).show()
                                }
                                dateOfBirth.isBlank() -> {
                                    Toast.makeText(context, "Select your date of birth", Toast.LENGTH_SHORT).show()
                                }
                                password.isBlank() -> {
                                    Toast.makeText(context, "Enter a password", Toast.LENGTH_SHORT).show()
                                }
                                password.length < 6 -> {
                                    Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                                }
                                confirmPassword != password -> {
                                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    viewModel.signUp(email, password, username, dateOfBirth)
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
                            Text("Create Account")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Already have account link
            Row {
                Text("Already have an account?", color = textColor)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Sign In",
                    color = DeepBlue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate("login")
                    }
                )
            }
        }
    }
}

// Data class for registration form
data class RegisterForm(
    var email: String = "",
    var username: String = "",
    var dateOfBirth: String = "",
    var password: String = "",
    var confirmPassword: String = ""
) {
    fun isValid(): Boolean {
        return email.isNotBlank() && email.contains("@") &&
                username.isNotBlank() &&
                dateOfBirth.isNotBlank() &&
                password.isNotBlank() && password.length >= 6 &&
                confirmPassword == password
    }
}