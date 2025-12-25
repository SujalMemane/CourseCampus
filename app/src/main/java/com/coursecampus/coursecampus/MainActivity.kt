package com.coursecampus.coursecampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.coursecampus.coursecampus.core.ui.theme.CourseCampusTheme
import com.coursecampus.coursecampus.navigation.AppNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {true}

        CoroutineScope(Dispatchers.Main).launch {
            delay(200L)
            splashScreen.setKeepOnScreenCondition { false }
        }

        setContent {
            CourseCampusTheme {
                AppNavigation()
            }
        }
    }
}

