package com.coursecampus.coursecampus.ui.navigation

/**
 * Sealed class defining all navigation routes in the app
 * Supports both simple and parameterized routes
 */
sealed class Screen(val route: String) {
    
    // Auth Flow
    data object Splash : Screen("splash")
    data object Login : Screen("login")
    data object Signup : Screen("signup")
    data object EmailVerification : Screen("email_verification")
    data object ForgotPassword : Screen("forgot_password")
    
    // Main App Flow (Bottom Navigation)
    data object Home : Screen("home")
    data object Courses : Screen("courses")
    data object Chatbot : Screen("chatbot")
    data object Saved : Screen("saved")
    data object Profile : Screen("profile")
    
    // Other Screens
    data object Settings : Screen("settings")
    
    // Parameterized Routes
    data object CourseDetails : Screen("course_details/{courseId}") {
        fun createRoute(courseId: String): String {
            return "course_details/$courseId"
        }
    }
    
    companion object {
        /**
         * Helper function to extract courseId from route
         */
        fun getCourseIdFromRoute(route: String?): String? {
            return route?.substringAfterLast("/")
        }
        
        /**
         * Check if current route belongs to main app (bottom nav screens)
         */
        fun isMainAppRoute(route: String?): Boolean {
            return when (route) {
                Home.route, Courses.route, Chatbot.route, Saved.route, Profile.route -> true
                else -> route?.startsWith("course_details/") == true
            }
        }
        
        /**
         * Get bottom navigation routes
         */
        val bottomNavRoutes = listOf(
            Home,
            Courses, 
            Chatbot,
            Saved,
            Profile
        )
    }
}