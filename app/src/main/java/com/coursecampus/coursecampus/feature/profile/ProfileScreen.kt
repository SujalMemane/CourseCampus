package com.coursecampus.coursecampus.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coursecampus.coursecampus.R
import com.coursecampus.coursecampus.domain.model.User

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onEditProfile: () -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF9F9F9)
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black

    val user by viewModel.userState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val userInfo = remember(user) {
        user?.let {
            listOf(
                "Gender" to it.gender,
                "Location" to it.location,
                "Category" to it.category,
                "Qualification" to it.qualification,
                "Year of Passing" to it.yearOfPassing,
                "University" to it.university,
                "Course" to it.course,
                "CGPA" to it.cgpa,
                "Interest" to it.interest,
                "Goals" to it.goals
            )
        } ?: emptyList()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
            ) {
                // Header Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 36.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = textColor
                    )
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "Menu",
                                tint = textColor
                            )
                        }
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(text = { Text("Edit Profile") }, onClick = {
                                expanded = false
                                onEditProfile()
                            })
                            DropdownMenuItem(text = { Text("Log Out") }, onClick = {
                                expanded = false
                                viewModel.logout()
                                onLogout()
                            })
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Profile Image + Name Card
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .offset(y = (-16).dp),
                ) {
                    val profileImage = if (user?.gender?.lowercase() == "female") R.drawable.ic_female else R.drawable.ic_male
                    Image(
                        painter = painterResource(id = profileImage),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth()
                    ) {
                        Text(
                            text = user?.name ?: "No Name",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = textColor
                        )
                        Text(
                            text = user?.email ?: "No Email",
                            fontSize = 14.sp,
                            color = textColor.copy(alpha = 0.6f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Info Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 56.dp, top = 16.dp)) {
                        items(userInfo) { (label, value) ->
                            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                                    color = textColor.copy(alpha = 0.7f)
                                )
                                Text(
                                    text = if (value.isEmpty()) "Not Set" else value,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = textColor
                                )
                                HorizontalDivider(modifier = Modifier.padding(top = 12.dp), color = textColor.copy(alpha = 0.1f))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
