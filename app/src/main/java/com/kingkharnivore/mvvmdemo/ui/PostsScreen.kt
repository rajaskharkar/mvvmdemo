package com.kingkharnivore.mvvmdemo.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kingkharnivore.mvvmdemo.model.Post
import com.kingkharnivore.mvvmdemo.viewmodel.PostViewModel

@Composable
fun PostScreen(viewModel: PostViewModel = viewModel()) {
    val posts by viewModel.posts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(
                    AppColors.DarkBackground,
                    AppColors.PurplePrimary.copy(alpha = 0.3f),
                    AppColors.DarkBackground
                )
            )
        )) {
        when {
            isLoading -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        color = AppColors.PurplePrimary,
                        strokeWidth = 4.dp
                    )
                    Text(
                        text = "Loading amazing content...",
                        style = MaterialTheme.typography.titleMedium,
                        color = AppColors.TextSecondary
                    )
                }
            }
            error != null -> {
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "⚠️",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = "Oops! Something went wrong",
                        style = MaterialTheme.typography.headlineSmall,
                        color = AppColors.TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = error!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.TextSecondary,
                        textAlign = TextAlign.Center
                    )
                    Button(
                        onClick = { viewModel.loadPosts() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.PurplePrimary
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text(
                            text = "Try Again",
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                        )
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier= Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(posts) { post ->
                        PostItem(post)
                    }
                }
            }
        }
    }
}

@Composable
fun PostItem(post: Post) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clickable {
                isPressed = !isPressed
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.CardBackground.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Box {
            // Gradient overlay on hover
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                AppColors.PurplePrimary.copy(alpha = if (isPressed) 0.1f else 0f),
                                AppColors.PinkAccent.copy(alpha = if (isPressed) 0.1f else 0f)
                            )
                        )
                    )
            )
            Column(modifier = Modifier.padding(20.dp)) {
                // Header Row with User Badge and Post ID
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // User Badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(
                                            AppColors.PurplePrimary,
                                            AppColors.PinkAccent
                                        )
                                    ),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "U${post.id}",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "User ${post.userId}",
                            color = AppColors.TextSecondary,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    // Post ID Badge
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = AppColors.PurplePrimary
                    ) {
                        Text(
                            text = "#${post.id}",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Title
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.TextPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Body
                Text(
                    text = post.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = AppColors.TextSecondary.copy(alpha = 0.8f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}