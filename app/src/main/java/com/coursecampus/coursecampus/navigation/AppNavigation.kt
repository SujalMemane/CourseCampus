package com.coursecampus.coursecampus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.coursecampus.coursecampus.feature.auth.ForgotPassword
import com.coursecampus.coursecampus.feature.auth.LoginScreen
import com.coursecampus.coursecampus.feature.auth.RegisterScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }
        composable("forgotPassword") { ForgotPassword(navController) }
        composable("mainScreen") { MainScreen(navController) }
    }
}
