package com.coursecampus.coursecampus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coursecampus.coursecampus.feature.auth.CreatePassword
import com.coursecampus.coursecampus.feature.auth.ForgotPassword
import com.coursecampus.coursecampus.feature.auth.LoginScreen
import com.example.coursecampus.ui.screens.CreateProfile

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("createProfile") { CreateProfile(navController) }
        composable("createPassword") { CreatePassword(navController) }
        composable("forgotPassword") { ForgotPassword(navController) }
        composable("mainScreen") { MainScreen(navController) }
    }
}
