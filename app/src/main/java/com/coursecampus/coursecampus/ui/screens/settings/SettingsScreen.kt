package com.coursecampus.coursecampus.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coursecampus.coursecampus.core.ui.theme.CourseCampusTheme

/**
 * Settings Screen - App preferences and account settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit
) {
    var darkModeEnabled by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }
    var downloadOnWifiOnly by remember { mutableStateOf(true) }
    var autoPlayVideos by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Settings") },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                SettingsSection(title = "Appearance") {
                    SettingsToggleItem(
                        icon = Icons.Default.DarkMode,
                        title = "Dark Mode",
                        subtitle = "Use dark theme",
                        checked = darkModeEnabled,
                        onCheckedChange = { darkModeEnabled = it }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Notifications") {
                    SettingsToggleItem(
                        icon = Icons.Default.Notifications,
                        title = "Push Notifications",
                        subtitle = "Receive course updates and reminders",
                        checked = notificationsEnabled,
                        onCheckedChange = { notificationsEnabled = it }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Downloads & Playback") {
                    SettingsToggleItem(
                        icon = Icons.Default.Wifi,
                        title = "Download on Wi-Fi only",
                        subtitle = "Save mobile data",
                        checked = downloadOnWifiOnly,
                        onCheckedChange = { downloadOnWifiOnly = it }
                    )
                    
                    SettingsToggleItem(
                        icon = Icons.Default.PlayArrow,
                        title = "Auto-play videos",
                        subtitle = "Automatically play next video",
                        checked = autoPlayVideos,
                        onCheckedChange = { autoPlayVideos = it }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Account") {
                    SettingsClickableItem(
                        icon = Icons.Default.Person,
                        title = "Edit Profile",
                        subtitle = "Update your personal information",
                        onClick = { /* TODO: Navigate to edit profile */ }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.Lock,
                        title = "Change Password",
                        subtitle = "Update your password",
                        onClick = { /* TODO: Navigate to change password */ }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.Email,
                        title = "Email Preferences",
                        subtitle = "Manage email notifications",
                        onClick = { /* TODO: Navigate to email preferences */ }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Storage") {
                    SettingsClickableItem(
                        icon = Icons.Default.Storage,
                        title = "Downloaded Courses",
                        subtitle = "Manage offline content",
                        onClick = { /* TODO: Navigate to downloads */ }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.CleaningServices,
                        title = "Clear Cache",
                        subtitle = "Free up storage space",
                        onClick = { /* TODO: Clear cache */ }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Support") {
                    SettingsClickableItem(
                        icon = Icons.Default.Help,
                        title = "Help Center",
                        subtitle = "Get help and support",
                        onClick = { /* TODO: Navigate to help */ }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.Feedback,
                        title = "Send Feedback",
                        subtitle = "Share your thoughts",
                        onClick = { /* TODO: Open feedback */ }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.Star,
                        title = "Rate App",
                        subtitle = "Rate us on the app store",
                        onClick = { /* TODO: Open app store */ }
                    )
                }
            }
            
            item {
                SettingsSection(title = "Legal") {
                    SettingsClickableItem(
                        icon = Icons.Default.Description,
                        title = "Terms of Service",
                        subtitle = "Read our terms",
                        onClick = { /* TODO: Open terms */ }
                    )
                    
                    SettingsClickableItem(
                        icon = Icons.Default.PrivacyTip,
                        title = "Privacy Policy",
                        subtitle = "Read our privacy policy",
                        onClick = { /* TODO: Open privacy policy */ }
                    )
                }
            }
            
            item {
                // App Version
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Course Campus",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = "Version 1.0.0",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column {
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(4.dp)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun SettingsToggleItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun SettingsClickableItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
        
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Navigate",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    CourseCampusTheme {
        SettingsScreen(onNavigateBack = {})
    }
}