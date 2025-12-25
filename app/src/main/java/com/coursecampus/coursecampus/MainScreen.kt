package com.coursecampus.coursecampus

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.navigation.NavController
import com.coursecampus.coursecampus.core.ui.theme.DeepBlue

@Composable
fun MainScreen(navController: NavController) {
    val navItemList = listOf(
        NavItem(label = "ChatBot", icon = R.drawable.ic_chat),
        NavItem(label = "Saved", icon = R.drawable.ic_bookmark),
        NavItem(label = "Home", icon = R.drawable.ic_home),
        NavItem(label = "Courses", icon = R.drawable.ic_graduation),
        NavItem(label = "Profile", icon = R.drawable.ic_male),
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    val isDarkTheme = isSystemInDarkTheme()
    val screenBackground = if (isDarkTheme) Color.Black else Color.White
    val navBarColor = if (isDarkTheme) Color(0xFF1C1C1E) else Color(0xFFF2F2F2)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(screenBackground),
        containerColor = screenBackground,
        bottomBar = {
            val screenWidth = LocalConfiguration.current.screenWidthDp.dp
            val itemWidth = screenWidth / navItemList.size

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                    .background(navBarColor)
                    .height(56.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    navItemList.forEachIndexed { index, navItem ->
                        val selected = selectedIndex == index

                        val iconColor by animateColorAsState(
                            targetValue = when {
                                navItem.label == "ChatBot" || navItem.label == "Profile" -> Color.Unspecified
                                selected -> DeepBlue
                                else -> Color.Gray
                            },
                            label = "iconTint"
                        )

                        val scale by animateFloatAsState(
                            targetValue = if (selected) 1.2f else 1f,
                            animationSpec = tween(300), label = "scale"
                        )

                        IconButton(
                            onClick = { selectedIndex = index },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            Icon(
                                painter = painterResource(id = navItem.icon),
                                contentDescription = navItem.label,
                                tint = iconColor,
                                modifier = Modifier
                                    .size(24.dp)
                                    .scale(scale)
                            )
                        }
                    }
                }

                val underlineOffsetX by animateDpAsState(
                    targetValue = selectedIndex * itemWidth + itemWidth / 2 - 12.dp,
                    animationSpec = tween(300), label = "underlineOffset"
                )

                Box(
                    modifier = Modifier
                        .offset(x = underlineOffsetX)
                        .width(24.dp)
                        .height(3.dp)
                        .background(DeepBlue, shape = RoundedCornerShape(50))
                        .align(Alignment.BottomStart)
                )
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedIndex)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {
    when (selectedIndex) {
        0 -> ChatBot()
        1 -> SavedScreen()
        2 -> HomeScreen()
        3 -> CoursesScreen()
        4 -> ProfileScreen()
    }
}
