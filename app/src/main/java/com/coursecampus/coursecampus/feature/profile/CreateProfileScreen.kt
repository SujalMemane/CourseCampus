package com.example.coursecampus.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.coursecampus.coursecampus.R

@Composable
fun CreateProfile(navController: NavController) {
    val isDark = isSystemInDarkTheme()
    val textColor = if (isDark) Color.White else Color.Black
    val deepBlue = Color(0xFF007AFF)

    val genderOptions = listOf("Male", "Female", "Other")
    val categoryOptions = listOf("General", "OBC", "SC", "ST")
    val qualificationOptions = listOf("High School", "Diploma", "Bachelor's", "Master's", "PhD")
    val yearOptions = (2000..2025).map { it.toString() }

    var form by remember { mutableStateOf(RegistrationForm()) }
    var isSubmitClicked by remember { mutableStateOf(false) }

    val fields = listOf(
        form.name, form.gender, form.address,
        form.category, form.highestQualification,
        form.yearOfPassing, form.universityName,
        form.courseName, form.marks, form.interestedField, form.goals
    )
    val completedFields = fields.count { it.isNotBlank() }
    val progress by animateFloatAsState(
        targetValue = completedFields / fields.size.toFloat(),
        label = "Progress"
    )

    val profilePic = when (form.gender) {
        "Male" -> R.drawable.ic_male
        "Female" -> R.drawable.ic_female
        else -> R.drawable.ic_other
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize().imePadding()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(top = 32.dp, bottom = 64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Create Profile",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)
                        ) {
                            Image(
                                painter = painterResource(id = profilePic),
                                contentDescription = "Profile Image",
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    item { CustomInput("Name", form.name, isSubmitClicked, deepBlue) { form = form.copy(name = it) } }
                    item {
                        CustomDropdown("Gender", genderOptions, form.gender, isSubmitClicked, deepBlue) {
                            form = form.copy(gender = it)
                        }
                    }
                    item { CustomInput("Address / Location", form.address, isSubmitClicked, deepBlue) { form = form.copy(address = it) } }
                    item {
                        CustomDropdown("Category", categoryOptions, form.category, isSubmitClicked, deepBlue) {
                            form = form.copy(category = it)
                        }
                    }
                    item {
                        CustomDropdown("Highest Qualification", qualificationOptions, form.highestQualification, isSubmitClicked, deepBlue) {
                            form = form.copy(highestQualification = it)
                        }
                    }
                    item {
                        CustomDropdown("Year of Passing", yearOptions, form.yearOfPassing, isSubmitClicked, deepBlue) {
                            form = form.copy(yearOfPassing = it)
                        }
                    }
                    item { CustomInput("University / College Name", form.universityName, isSubmitClicked, deepBlue) { form = form.copy(universityName = it) } }
                    item { CustomInput("Course / Degree Name", form.courseName, isSubmitClicked, deepBlue) { form = form.copy(courseName = it) } }
                    item { CustomInput("Marks / CGPA", form.marks, isSubmitClicked, deepBlue) { form = form.copy(marks = it) } }
                    item { CustomInput("Interested Field / Domain", form.interestedField, isSubmitClicked, deepBlue) { form = form.copy(interestedField = it) } }
                    item { CustomInput("Career or Learning Goals", form.goals, isSubmitClicked, deepBlue) { form = form.copy(goals = it) } }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                val iconSize = 48.dp
                val progressBarMaxWidth = LocalConfiguration.current.screenWidthDp.dp - 32.dp
                val animatedProgress by animateDpAsState(
                    targetValue = (progressBarMaxWidth * progress).coerceAtMost(progressBarMaxWidth - iconSize / 2),
                    label = "ProgressBarWidth"
                )

                // Main progress bar with rounded end
                Row(
                    modifier = Modifier
                        .height(48.dp)
                        .clip(RoundedCornerShape(48.dp))
                        .background(deepBlue)
                        .width(animatedProgress + iconSize / 2) // This makes the bar extend to icon center
                ) {}

                // Floating Icon at the tip
                IconButton(
                    onClick = {
                        isSubmitClicked = true
                        if (form.isValid()) {
                            navController.navigate("createPassword")
                        }
                    },
                    modifier = Modifier
                        .size(iconSize)
                        .offset(x = animatedProgress-24.dp)
                        .align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_next),
                        contentDescription = "Next",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomInput(label: String, value: String, isSubmitClicked: Boolean, focusedColor: Color, onValueChange: (String) -> Unit) {
    val isDark = isSystemInDarkTheme()
    val textColor = if (isDark) Color.White else Color.Black
    val isError = value.isBlank() && isSubmitClicked

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = if (isError) Color.Red else textColor) },
        isError = isError,
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        textStyle = androidx.compose.ui.text.TextStyle(color = textColor),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isError) Color.Red else focusedColor,
            unfocusedBorderColor = if (isError) Color.Red else textColor,
            errorBorderColor = Color.Red
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(label: String, options: List<String>, selected: String, isSubmitClicked: Boolean, focusedColor: Color, onSelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val isDark = isSystemInDarkTheme()
    val textColor = if (isDark) Color.White else Color.Black
    val isError = selected.isBlank() && isSubmitClicked

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(label, color = if (isError) Color.Red else textColor) },
            trailingIcon = { Icon(Icons.Filled.KeyboardArrowDown, null, tint = textColor) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            shape = RoundedCornerShape(12.dp),
            textStyle = androidx.compose.ui.text.TextStyle(color = textColor),
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (isError) Color.Red else focusedColor,
                unfocusedBorderColor = if (isError) Color.Red else textColor,
                errorBorderColor = Color.Red.copy(0.8f)
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }

        ) {
            options.forEach {
                DropdownMenuItem(
                    text = { Text(it, color = textColor) },
                    onClick = {
                        onSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

// Data class for form
data class RegistrationForm(
    var name: String = "",
    var gender: String = "Other",
    var address: String = "",
    var category: String = "",
    var highestQualification: String = "",
    var yearOfPassing: String = "",
    var universityName: String = "",
    var courseName: String = "",
    var marks: String = "",
    var interestedField: String = "",
    var goals: String = ""
) {
    fun isValid(): Boolean {
        return name.isNotBlank() &&
                gender.isNotBlank() &&
                address.isNotBlank() &&
                category.isNotBlank() &&
                highestQualification.isNotBlank() &&
                yearOfPassing.isNotBlank() &&
                universityName.isNotBlank() &&
                courseName.isNotBlank() &&
                marks.isNotBlank() &&
                interestedField.isNotBlank() &&
                goals.isNotBlank()
    }
}