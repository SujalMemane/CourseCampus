package com.coursecampus.coursecampus

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun ProfileScreen() {
    var expanded by remember { mutableStateOf(false) }
    val isDarkTheme = isSystemInDarkTheme()
    val backgroundColor = if (isDarkTheme) Color(0xFF121212) else Color(0xFFF9F9F9)
    val cardColor = if (isDarkTheme) Color(0xFF1E1E1E) else Color.White
    val textColor = if (isDarkTheme) Color.White else Color.Black

    val userInfo = listOf(
        "Gender" to "Male",
        "Location" to "New York",
        "Category" to "General",
        "Qualification" to "B.Tech",
        "Year of Passing" to "2022",
        "University" to "XYZ University",
        "Course" to "Computer Science",
        "CGPA" to "8.5",
        "Interest" to "Data Science",
        "Goals" to "Become an AI expert"
    )

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
                        // TODO: Navigate to CreateAccountScreen in edit mode
                    })
                    DropdownMenuItem(text = { Text("Log Out") }, onClick = {
                        expanded = false
                        // TODO: Handle log out logic
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp)) // Add padding between header and profile image/text row

        // Profile Image + Name Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .offset(y = -16.dp), // Add offset to center the row)// Padding to the row
        ) {
            // Profile Image
            Image(
                painter = painterResource(id = R.drawable.ic_male), // Change dynamically by gender
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp)) // Space between image and text

            // Text Information
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.align(Alignment.CenterVertically).fillMaxWidth() // Center align text vertically
            ) {
                Text(
                    text = "Sujal Memane",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor
                )
                Text(
                    text = "xyz@gmail.com",
                    fontSize = 14.sp,
                    color = textColor.copy(alpha = 0.6f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add padding below the profile image/text row

        // Info Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(start = 16.dp, end = 16.dp,bottom = 56.dp, top = 16.dp)) {
                items(userInfo) { (label, value) ->
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = textColor.copy(alpha = 0.7f)
                        )
                        Text(
                            text = value,
                            style = MaterialTheme.typography.bodyLarge,
                            color = textColor
                        )
                        Divider(modifier = Modifier.padding(top = 12.dp), color = textColor.copy(alpha = 0.1f))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp)) // Add padding below the card
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProfilePreview() {
    ProfileScreen()
}