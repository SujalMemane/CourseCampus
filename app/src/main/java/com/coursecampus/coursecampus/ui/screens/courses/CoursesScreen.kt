package com.coursecampus.coursecampus.ui.screens.courses

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coursecampus.coursecampus.R
import com.coursecampus.coursecampus.core.ui.theme.CourseCampusTheme
import com.coursecampus.coursecampus.ui.screens.home.Course

/**
 * Courses Screen - Browse all courses with filter and sort options
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoursesScreen(
    onNavigateToCourseDetails: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var isGridView by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    
    val courses = getAllCourses()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = { Text("All Courses") },
            actions = {
                IconButton(onClick = { showFilterSheet = true }) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter"
                    )
                }
                IconButton(onClick = { isGridView = !isGridView }) {
                    Icon(
                        imageVector = if (isGridView) Icons.Default.List else Icons.Default.GridView,
                        contentDescription = if (isGridView) "List View" else "Grid View"
                    )
                }
            }
        )
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search courses...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Results Count
            Text(
                text = "${courses.size} courses found",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Courses List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(courses) { course ->
                    CourseListItem(
                        course = course,
                        onClick = { onNavigateToCourseDetails(course.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun CourseListItem(
    course: Course,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
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
        }
    }
}

private fun getAllCourses(): List<Course> {
    return listOf(
        Course("1", "Complete Android Development with Kotlin", "John Smith", "12 hours", 4.8, 1250, 89.99, R.drawable.ic_graduation, "Programming"),
        Course("2", "UI/UX Design Fundamentals", "Sarah Johnson", "8 hours", 4.7, 890, 0.0, R.drawable.ic_graduation, "Design"),
        Course("3", "Digital Marketing Masterclass", "Mike Wilson", "15 hours", 4.9, 2100, 129.99, R.drawable.ic_graduation, "Marketing"),
        Course("4", "Data Science with Python", "Dr. Emily Chen", "20 hours", 4.6, 1580, 149.99, R.drawable.ic_graduation, "Data Science"),
        Course("5", "Business Strategy & Planning", "Robert Davis", "10 hours", 4.5, 750, 79.99, R.drawable.ic_graduation, "Business"),
        Course("6", "React Native Mobile Development", "Alex Turner", "14 hours", 4.7, 980, 99.99, R.drawable.ic_graduation, "Programming"),
        Course("7", "Graphic Design Mastery", "Lisa Park", "16 hours", 4.8, 1340, 119.99, R.drawable.ic_graduation, "Design"),
        Course("8", "SEO & Content Marketing", "David Brown", "9 hours", 4.6, 670, 0.0, R.drawable.ic_graduation, "Marketing")
    )
}

@Preview(showBackground = true)
@Composable
private fun CoursesScreenPreview() {
    CourseCampusTheme {
        CoursesScreen(onNavigateToCourseDetails = {})
    }
}