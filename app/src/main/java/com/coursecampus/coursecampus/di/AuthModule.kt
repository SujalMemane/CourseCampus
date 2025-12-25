package com.coursecampus.coursecampus.di

import com.coursecampus.coursecampus.data.repository.AuthRepository
import com.coursecampus.coursecampus.data.repository.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firebaseDatabase)
    }
}
