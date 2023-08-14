package br.com.alexfelipe.devid.ui.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.alexfelipe.devid.models.Skill
import br.com.alexfelipe.devid.samples.sampleSkills
import br.com.alexfelipe.devid.ui.theme.DevIdTheme
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    profilePic: String,
    userName: String,
    bio: String,
    modifier: Modifier = Modifier,
    skills: List<Skill> = listOf(),
    isShowSkills: Boolean = false
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFF673AB7),
                            Color(0xFF2196F3),
                            Color(0xFF009688),
                        ),

                        )
                )
                .height(200.dp)
        ) {
            var clicked by remember {
                mutableStateOf(false)
            }
            val animatedPercent by animateIntAsState(
                targetValue = if (clicked) 0 else 100,
                label = "main image rounded corner"
            )
            val animatedImageWidth by animateFloatAsState(
                targetValue = if (clicked) 1f else 0.25f,
                label = "image width"
            )
            val animatedImageHeight by animateFloatAsState(
                targetValue = if (clicked) 1f else 0.5f,
                label = "image width"
            )
            AsyncImage(
                model = profilePic,
                contentDescription = "profile pic",
                Modifier
                    .clip(RoundedCornerShape(animatedPercent))
                    .align(Alignment.Center)
                    .fillMaxWidth(animatedImageWidth)
                    .fillMaxHeight(animatedImageHeight)
                    .clickable {
                        clicked = !clicked
                    },
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Color.Gray)
            )
        }
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = userName,
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            var textClick by remember {
                mutableStateOf(false)
            }
            Text(
                text = bio,
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        textClick = !textClick
                    },
                fontSize = 18.sp,
                maxLines = if (textClick) Int.MAX_VALUE else 3,
                overflow = TextOverflow.Ellipsis,
            )
        }
        var isShowSkills by remember {
            mutableStateOf(isShowSkills)
        }
        Row(
            Modifier
                .padding(8.dp)
                .clip(CircleShape)
                .padding(8.dp)
                .clickable {
                    isShowSkills = !isShowSkills
                },
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = "Flecha para a direita",
                Modifier.rotate(
                    animateFloatAsState(
                        targetValue = if (isShowSkills) 90f else 0f,
                        label = "arrow right rotation",
                    ).value
                )
            )
            Text(
                text = "My skills", Modifier,
                fontSize = 18.sp
            )
        }
        AnimatedVisibility(visible = isShowSkills) {
            Column {
                for (skill in skills) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        var level by remember {
                            mutableStateOf(0f)
                        }
                        val animatedLevel by animateFloatAsState(
                            targetValue = level,
                            animationSpec = keyframes {
                                this.durationMillis = 500
                            },
                            label = "animated level"
                        )
                        LaunchedEffect(null) {
                            delay(100)
                            level = skill.level
                        }
                        CircularProgressIndicator(
                            animatedLevel,
                            color = when {
                                level < 0.25f -> Color(0xFFD61717)
                                level < 0.50f -> Color(0xFFFF9800)
                                level < 0.75f -> Color(0xFF3F51B5)
                                else -> Color(0xFF009688)
                            }
                        )
                        Text(text = skill.name)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    DevIdTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box {
                MainScreen(
                    "https://avatars.githubusercontent.com/u/8989346?v=4",
                    "Alex Felipe",
                    "I'm a software developer, so let's start the code"
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenWithSkillsPreview() {
    DevIdTheme {
        Surface(
            Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box {
                MainScreen(
                    "https://avatars.githubusercontent.com/u/8989346?v=4",
                    "Alex Felipe",
                    "I'm a software developer, so let's start the code",
                    isShowSkills = true,
                    skills = sampleSkills
                )
            }
        }
    }
}