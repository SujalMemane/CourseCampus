package com.coursecampus.coursecampus.ui.screens.chatbot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coursecampus.coursecampus.core.ui.theme.CourseCampusTheme
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

/**
 * Chatbot Screen - AI assistant for course-related queries
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen() {
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(getInitialMessages()) }
    var isTyping by remember { mutableStateOf(false) }
    
    val listState = rememberLazyListState()
    
    // Auto scroll to bottom when new message is added
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = "Course Assistant",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Online • Ready to help",
                        fontSize = 12.sp,
                        color = Color(0xFF4CAF50)
                    )
                }
            }
        )
        
        // Messages List
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(messages) { message ->
                MessageBubble(message = message)
            }
            
            // Typing indicator
            if (isTyping) {
                item {
                    TypingIndicator()
                }
            }
        }
        
        // Message Input
        MessageInput(
            messageText = messageText,
            onMessageChange = { messageText = it },
            onSendMessage = {
                if (messageText.isNotBlank()) {
                    // Add user message
                    val userMessage = ChatMessage(
                        id = UUID.randomUUID().toString(),
                        text = messageText,
                        isFromUser = true,
                        timestamp = System.currentTimeMillis()
                    )
                    messages = messages + userMessage
                    
                    val currentMessage = messageText
                    messageText = ""
                    
                    // Simulate AI response
                    isTyping = true
                    
                    kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Main).launch {
                        delay(1500) // Simulate thinking time
                        
                        val aiResponse = generateAIResponse(currentMessage)
                        val aiMessage = ChatMessage(
                            id = UUID.randomUUID().toString(),
                            text = aiResponse,
                            isFromUser = false,
                            timestamp = System.currentTimeMillis()
                        )
                        
                        isTyping = false
                        messages = messages + aiMessage
                    }
                }
            }
        )
    }
}

@Composable
private fun MessageBubble(message: ChatMessage) {
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromUser) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isFromUser) {
            // AI Avatar
            Card(
                modifier = Modifier.size(32.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "AI",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        Column(
            modifier = Modifier.widthIn(max = 280.dp),
            horizontalAlignment = if (message.isFromUser) Alignment.End else Alignment.Start
        ) {
            Card(
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (message.isFromUser) 16.dp else 4.dp,
                    bottomEnd = if (message.isFromUser) 4.dp else 16.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = if (message.isFromUser) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = message.text,
                    modifier = Modifier.padding(12.dp),
                    color = if (message.isFromUser) 
                        MaterialTheme.colorScheme.onPrimary 
                    else 
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
            
            Text(
                text = timeFormat.format(Date(message.timestamp)),
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
        
        if (message.isFromUser) {
            Spacer(modifier = Modifier.width(8.dp))
            // User Avatar
            Card(
                modifier = Modifier.size(32.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "U",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
        }
    }
}

@Composable
private fun TypingIndicator() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "AI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    var alpha by remember { mutableFloatStateOf(0.3f) }
                    
                    LaunchedEffect(Unit) {
                        while (true) {
                            delay(index * 200L)
                            alpha = 1f
                            delay(600)
                            alpha = 0.3f
                            delay(600)
                        }
                    }
                    
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(
                                MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = alpha),
                                RoundedCornerShape(3.dp)
                            )
                    )
                    
                    if (index < 2) {
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun MessageInput(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendMessage: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = onMessageChange,
                placeholder = { Text("Ask me about courses...") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(24.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                maxLines = 4
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            FloatingActionButton(
                onClick = onSendMessage,
                modifier = Modifier.size(48.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

/**
 * Chat Message Data Class
 */
data class ChatMessage(
    val id: String,
    val text: String,
    val isFromUser: Boolean,
    val timestamp: Long
)

/**
 * Generate AI response based on user input
 */
private fun generateAIResponse(userMessage: String): String {
    val lowerMessage = userMessage.lowercase()
    
    return when {
        lowerMessage.contains("hello") || lowerMessage.contains("hi") -> 
            "Hello! I'm your Course Campus assistant. I can help you find courses, answer questions about our platform, or provide learning recommendations. What would you like to know?"
        
        lowerMessage.contains("course") && lowerMessage.contains("recommend") ->
            "I'd be happy to recommend courses! What subject are you interested in? We have excellent courses in Programming, Design, Business, Marketing, and Data Science."
        
        lowerMessage.contains("programming") || lowerMessage.contains("coding") ->
            "Great choice! For programming, I recommend starting with 'Complete Android Development with Kotlin' or 'React Native Mobile Development'. Both are highly rated and perfect for beginners to advanced learners."
        
        lowerMessage.contains("design") ->
            "For design, check out 'UI/UX Design Fundamentals' - it's currently free! We also have 'Graphic Design Mastery' which covers advanced techniques."
        
        lowerMessage.contains("free") ->
            "We have several free courses available! 'UI/UX Design Fundamentals' and 'SEO & Content Marketing' are both completely free and highly rated."
        
        lowerMessage.contains("price") || lowerMessage.contains("cost") ->
            "Our courses range from free to $149.99. We offer great value with lifetime access, certificates, and expert instruction. Would you like to know about any specific course pricing?"
        
        lowerMessage.contains("certificate") ->
            "Yes! All our paid courses include a certificate of completion. You'll receive it once you finish all course modules and pass the final assessment."
        
        lowerMessage.contains("help") ->
            "I'm here to help! You can ask me about:\n• Course recommendations\n• Pricing and certificates\n• Learning paths\n• Technical support\n• Platform features\n\nWhat would you like to know?"
        
        else ->
            "That's an interesting question! While I can help with course recommendations, pricing, and platform features, I might not have all the answers. Is there something specific about our courses or platform I can help you with?"
    }
}

/**
 * Initial welcome messages
 */
private fun getInitialMessages(): List<ChatMessage> {
    return listOf(
        ChatMessage(
            id = "1",
            text = "Welcome to Course Campus! 👋\n\nI'm your AI learning assistant. I can help you:\n• Find the perfect courses\n• Get learning recommendations\n• Answer questions about our platform\n\nWhat would you like to learn today?",
            isFromUser = false,
            timestamp = System.currentTimeMillis() - 60000
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun ChatbotScreenPreview() {
    CourseCampusTheme {
        ChatbotScreen()
    }
}