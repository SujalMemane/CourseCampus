package com.coursecampus.coursecampus.ui.screens.saved

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coursecampus.coursecampus.R
import com.coursecampus.coursecampus.core.ui.theme.CourseCampusTheme
import com.coursecampus.coursecampus.ui.screens.home.Course

/**
 * Saved Screen - Shows bookmarked/saved courses
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    onNavigateToCourseDetails: (String) -> Unit
) {
    var savedCourses by remember { mutableStateOf(getSavedCourses()) }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("Saved Courses") }
        )
        
        if (savedCourses.isEmpty()) {
            // Empty State
            EmptyState()
        } else {
            // Saved Courses List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "${savedCourses.size} saved courses",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                
                items(savedCourses) { course ->
                    SavedCourseItem(
                        course = course,
                        onCourseClick = { onNavigateToCourseDetails(course.id) },
                        onRemoveFromSaved = { courseToRemove ->
                            savedCourses = savedCourses.filter { it.id != courseToRemove.id }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Empty State Icon
        Card(
            modifier = Modifier.size(120.dp),
            shape = RoundedCornerShape(60.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.School,
                    contentDescription = "No saved courses",
                    modifier = Modifier.size(60.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = "No Saved Courses Yet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Start exploring courses and save the ones you're interested in. They'll appear here for easy access.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { /* TODO: Navigate to courses */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Browse Courses")
        }
    }
}

@Composable
private fun SavedCourseItem(
    course: Course,
    onCourseClick: () -> Unit,
    onRemoveFromSaved: (Course) -> Unit
) {
    var showRemoveDialog by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCourseClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            // Course Thumbnail
            Image(
                painter = painterResource(id = course.imageRes),
                contentDescription = course.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Course Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = course.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Text(
                    text = "by ${course.instructor}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⭐ ${course.rating}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = " • ${course.duration}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = if (course.price == 0.0) "Free" else "$${course.price}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (course.price == 0.0) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
                )
            }
            
            // Remove Button
            IconButton(
                onClick = { showRemoveDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Default.BookmarkRemove,
                    contentDescription = "Remove from saved",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    
    // Remove Confirmation Dialog
    if (showRemoveDialog) {
        AlertDialog(
            onDismissRequest = { showRemoveDialog = false },
            title = { Text("Remove from Saved") },
            text = { Text("Are you sure you want to remove '${course.title}' from your saved courses?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRemoveFromSaved(course)
                        showRemoveDialog = false
                    }
                ) {
                    Text("Remove", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

/**
 * Sample saved courses data
 */
private fun getSavedCourses(): List<Course> {
    return listOf(
        Course("1", "Complete Android Development with Kotlin", "John Smith", "12 hours", 4.8, 1250, 89.99, R.drawable.ic_graduation, "Programming"),
        Course("2", "UI/UX Design Fundamentals", "Sarah Johnson", "8 hours", 4.7, 890, 0.0, R.drawable.ic_graduation, "Design"),
        Course("4", "Data Science with Python", "Dr. Emily Chen", "20 hours", 4.6, 1580, 149.99, R.drawable.ic_graduation, "Data Science")
    )
}

@Preview(showBackground = true)
@Composable
private fun SavedScreenPreview() {
    CourseCampusTheme {
        SavedScreen(onNavigateToCourseDetails = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun SavedScreenEmptyPreview() {
    CourseCampusTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(title = { Text("Saved Courses") })
            EmptyState()
        }
    }
}