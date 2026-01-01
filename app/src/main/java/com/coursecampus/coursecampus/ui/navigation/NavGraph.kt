package com.coursecampus.coursecampus.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.coursecampus.coursecampus.R
import com.coursecampus.coursecampus.ui.screens.auth.forgot.ForgotPasswordScreen
import com.coursecampus.coursecampus.ui.screens.auth.login.LoginScreen
import com.coursecampus.coursecampus.ui.screens.auth.signup.SignupScreen
import com.coursecampus.coursecampus.ui.screens.auth.verify.EmailVerificationScreen
import com.coursecampus.coursecampus.ui.screens.chatbot.ChatbotScreen
import com.coursecampus.coursecampus.ui.screens.courses.CoursesScreen
import com.coursecampus.coursecampus.ui.screens.details.CourseDetailsScreen
import com.coursecampus.coursecampus.ui.screens.home.HomeScreen
import com.coursecampus.coursecampus.ui.screens.profile.ProfileScreen
import com.coursecampus.coursecampus.ui.screens.saved.SavedScreen
import com.coursecampus.coursecampus.ui.screens.settings.SettingsScreen
import com.coursecampus.coursecampus.ui.screens.splash.SplashScreen

/**
 * Main navigation composable that handles the entire app navigation
 * Uses single NavController and NavHost as per best practices
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route
    
    // Determine if bottom navigation should be visible
    val showBottomNav = Screen.isMainAppRoute(currentRoute)
    
    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNav,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavigationBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Splash Screen - decides start destination based on auth state
            composable(Screen.Splash.route) {
                SplashScreen(
                    onNavigateToLogin = {
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    },
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Splash.route) { inclusive = true }
                        }
                    }
                )
            }
            
            // Auth Flow
            composable(Screen.Login.route) {
                LoginScreen(
                    onNavigateToSignup = {
                        navController.navigate(Screen.Signup.route)
                    },
                    onNavigateToForgotPassword = {
                        navController.navigate(Screen.ForgotPassword.route)
                    },
                    onLoginSuccess = {
                        // Clear back stack and navigate to home
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    }
                )
            }
            
            composable(Screen.Signup.route) {
                SignupScreen(
                    onNavigateToLogin = {
                        navController.popBackStack()
                    },
                    onNavigateToVerification = {
                        navController.navigate(Screen.EmailVerification.route)
                    }
                )
            }
            
            composable(Screen.EmailVerification.route) {
                EmailVerificationScreen(
                    onVerificationSuccess = {
                        // Clear back stack and navigate to home
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Signup.route) { inclusive = true }
                        }
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            
            composable(Screen.ForgotPassword.route) {
                ForgotPasswordScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onResetSuccess = {
                        navController.popBackStack()
                    }
                )
            }
            
            // Main App Screens (Bottom Navigation)
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToCourseDetails = { courseId ->
                        navController.navigate(Screen.CourseDetails.createRoute(courseId))
                    }
                )
            }
            
            composable(Screen.Courses.route) {
                CoursesScreen(
                    onNavigateToCourseDetails = { courseId ->
                        navController.navigate(Screen.CourseDetails.createRoute(courseId))
                    }
                )
            }
            
            composable(Screen.Chatbot.route) {
                ChatbotScreen()
            }
            
            composable(Screen.Saved.route) {
                SavedScreen(
                    onNavigateToCourseDetails = { courseId ->
                        navController.navigate(Screen.CourseDetails.createRoute(courseId))
                    }
                )
            }
            
            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToSettings = {
                        navController.navigate(Screen.Settings.route)
                    },
                    onLogout = {
                        // Clear back stack and return to login
                        navController.navigate(Screen.Login.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            
            // Other Screens
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            
            // Parameterized Route - Course Details
            composable(Screen.CourseDetails.route) { backStackEntry ->
                val courseId = backStackEntry.arguments?.getString("courseId") ?: ""
                CourseDetailsScreen(
                    courseId = courseId,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

/**
 * Bottom Navigation Bar component
 * Syncs with current route and handles navigation
 */
@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
    currentRoute: String?
) {
    NavigationBar {
        Screen.bottomNavRoutes.forEach { screen ->
            val isSelected = currentRoute == screen.route
            
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = getIconForScreen(screen)),
                        contentDescription = getScreenTitle(screen)
                    )
                },
                label = {
                    Text(getScreenTitle(screen))
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(screen.route) {
                            // Pop up to the start destination to avoid building up a large stack
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

/**
 * Helper function to get icon resource for each screen
 */
private fun getIconForScreen(screen: Screen): Int {
    return when (screen) {
        Screen.Home -> R.drawable.ic_home
        Screen.Courses -> R.drawable.ic_graduation
        Screen.Chatbot -> R.drawable.ic_chat
        Screen.Saved -> R.drawable.ic_bookmark
        Screen.Profile -> R.drawable.ic_male // Will be dynamic based on user
        else -> R.drawable.ic_home
    }
}

/**
 * Helper function to get screen title for bottom navigation
 */
private fun getScreenTitle(screen: Screen): String {
    return when (screen) {
        Screen.Home -> "Home"
        Screen.Courses -> "Courses"
        Screen.Chatbot -> "Chat"
        Screen.Saved -> "Saved"
        Screen.Profile -> "Profile"
        else -> ""
    }
}