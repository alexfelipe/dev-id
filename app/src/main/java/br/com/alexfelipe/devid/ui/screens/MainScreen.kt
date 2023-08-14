package br.com.alexfelipe.devid.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alexfelipe.devid.models.Skill
import br.com.alexfelipe.devid.ui.theme.DevIdTheme
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    profilePic: String,
    userName: String,
    bio: String,
    skills: List<Skill> = listOf()
) {
    Column(
        Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        ) {
            var clicked by remember {
                mutableStateOf(false)
            }
            var imageRoundedCorner by remember(clicked) {
                mutableStateOf(if (clicked) 100 else 0)
            }
            val imageRoundedCornerAnimated by animateIntAsState(
                targetValue = imageRoundedCorner,
                label = "main image rounded corner"
            )
            var imageWidth by remember(clicked) {
                mutableStateOf(if (clicked) 0.25f else 1f)
            }
            val imageWidthAnimated by animateFloatAsState(
                targetValue = imageWidth,
                label = "image width"
            )
            AsyncImage(
                model = profilePic,
                contentDescription = "profile pic",
                Modifier
                    .clip(RoundedCornerShape(imageRoundedCornerAnimated))
                    .align(Alignment.Center)
                    .fillMaxWidth(imageWidthAnimated)
                    .clickable {
                        clicked = !clicked
                    },
                contentScale = ContentScale.Crop,
                placeholder = ColorPainter(Color.Gray)
            )
        }
        Text(text = userName)
        Text(text = bio)
        var isShowSkills by remember {
            mutableStateOf(false)
        }
        var arrowRotate by remember {
            mutableStateOf(0f)
        }
        val arrowRotateAnimation by
        animateFloatAsState(
            targetValue = arrowRotate,
            label = "arrow right rotation",
        )
        Row(
            Modifier
                .fillMaxWidth()
                .clickable {
                    isShowSkills = !isShowSkills
                    arrowRotate = if (isShowSkills) 90f else 0f
                },
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowRight,
                contentDescription = "Flecha para a direita",
                Modifier.rotate(arrowRotateAnimation)
            )
            Text(text = "My skills")
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
                        var circularProgressColor by remember(skill.level) {
                            mutableStateOf(
                                when {
                                    skill.level < 0.25f -> Color(0xFFD61717)
                                    skill.level < 0.50f -> Color(0xFFFF9800)
                                    skill.level < 0.75f -> Color(0xFF3F51B5)
                                    else -> Color(0xFF009688)
                                }
                            )
                        }
                        val animatedCircularProgressColor by animateColorAsState(
                            targetValue = circularProgressColor,
                            label = "circular progress color"
                        )
                        CircularProgressIndicator(animatedLevel, color = animatedCircularProgressColor)
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