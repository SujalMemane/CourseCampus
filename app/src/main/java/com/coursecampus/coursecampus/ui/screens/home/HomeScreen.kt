package com.coursecampus.coursecampus.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

/**
 * Home Screen - Main dashboard with search, categories, and featured courses
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToCourseDetails: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    
    val categories = listOf("All", "Programming", "Design", "Business", "Marketing", "Data Science")
    val featuredCourses = getFeaturedCourses()
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Good morning! 👋",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "Course Campus",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                // Search Bar
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = { /* TODO: Implement search */ }
                )
            }
            
            item {
                // Category Chips
                CategorySection(
                    categories = categories,
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }
            
            item {
                // Featured Courses Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Featured Courses",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    TextButton(onClick = { /* TODO: Navigate to all courses */ }) {
                        Text("See All")
                    }
                }
            }
            
            items(featuredCourses) { course ->
                CourseCard(
                    course = course,
                    onClick = { onNavigateToCourseDetails(course.id) }
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Search courses...") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
        )
    )
}

@Composable
private fun CategorySection(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Column {
        Text(
            text = "Categories",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 12.dp)
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                CategoryChip(
                    category = category,
                    isSelected = category == selectedCategory,
                    onClick = { onCategorySelected(category) }
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        onClick = onClick,
        label = { Text(category) },
        selected = isSelected,
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun CourseCard(
    course: Course,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Course Image
            Image(
                painter = painterResource(id = course.imageRes),
                contentDescription = course.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
            
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Course Title
                Text(
                    text = course.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Instructor
                Text(
                    text = "by ${course.instructor}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Course Info Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Duration and Rating
                    Column {
                        Text(
                            text = course.duration,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⭐ ${course.rating}",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                            Text(
                                text = " (${course.reviews})",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }
                    
                    // Price
                    Text(
                        text = if (course.price == 0.0) "Free" else "$${course.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (course.price == 0.0) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

/**
 * Course Data Class
 */
data class Course(
    val id: String,
    val title: String,
    val instructor: String,
    val duration: String,
    val rating: Double,
    val reviews: Int,
    val price: Double,
    val imageRes: Int,
    val category: String
)

/**
 * Sample featured courses data
 */
private fun getFeaturedCourses(): List<Course> {
    return listOf(
        Course(
            id = "1",
            title = "Complete Android Development with Kotlin",
            instructor = "John Smith",
            duration = "12 hours",
            rating = 4.8,
            reviews = 1250,
            price = 89.99,
            imageRes = R.drawable.ic_graduation,
            category = "Programming"
        ),
        Course(
            id = "2",
            title = "UI/UX Design Fundamentals",
            instructor = "Sarah Johnson",
            duration = "8 hours",
            rating = 4.7,
            reviews = 890,
            price = 0.0,
            imageRes = R.drawable.ic_graduation,
            category = "Design"
        ),
        Course(
            id = "3",
            title = "Digital Marketing Masterclass",
            instructor = "Mike Wilson",
            duration = "15 hours",
            rating = 4.9,
            reviews = 2100,
            price = 129.99,
            imageRes = R.drawable.ic_graduation,
            category = "Marketing"
        ),
        Course(
            id = "4",
            title = "Data Science with Python",
            instructor = "Dr. Emily Chen",
            duration = "20 hours",
            rating = 4.6,
            reviews = 1580,
            price = 149.99,
            imageRes = R.drawable.ic_graduation,
            category = "Data Science"
        ),
        Course(
            id = "5",
            title = "Business Strategy & Planning",
            instructor = "Robert Davis",
            duration = "10 hours",
            rating = 4.5,
            reviews = 750,
            price = 79.99,
            imageRes = R.drawable.ic_graduation,
            category = "Business"
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    CourseCampusTheme {
        HomeScreen(
            onNavigateToCourseDetails = {}
        )
    }
}