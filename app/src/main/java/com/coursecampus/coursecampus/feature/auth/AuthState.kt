package com.coursecampus.coursecampus.feature.auth

import com.google.firebase.auth.FirebaseUser

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val user: FirebaseUser? = null,
    val error: String? = null,
    val passwordResetSent: Boolean = false
)