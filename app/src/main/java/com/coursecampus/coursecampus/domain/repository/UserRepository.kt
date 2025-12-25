package com.coursecampus.coursecampus.domain.repository

import com.coursecampus.coursecampus.domain.model.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val database: FirebaseDatabase
) {
    private val usersRef = database.getReference("users")

    suspend fun getUserProfile(uid: String): User? {
        return try {
            val snapshot = usersRef.child(uid).get().await()
            snapshot.getValue(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveUserProfile(user: User): Boolean {
        return try {
            usersRef.child(user.uid).setValue(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
