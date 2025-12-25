package com.coursecampus.coursecampus.data.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun signUp(email: String, password: String, username: String, dateOfBirth: String): Result<FirebaseUser>
    suspend fun signIn(email: String, password: String): Result<FirebaseUser>
    suspend fun resetPassword(email: String): Result<Unit>
    fun signOut()
    fun getCurrentUser(): FirebaseUser?
    fun isUserLoggedIn(): Boolean
}
