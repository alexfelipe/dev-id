package br.com.alexfelipe.devid.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
    skills: List<Skill> = listOf()
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        BoxWithConstraints(
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
                .height(250.dp)
        ) {
            AsyncImage(
                model = profilePic,
                contentDescription = "profile pic",
                Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center)
                    .size(this.maxHeight / 2),
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
            Text(
                text = bio,
                Modifier
                    .fillMaxWidth(),
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis,
            )
        }
        var isShowSkills by remember {
            mutableStateOf(false)
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
                        LaunchedEffect(null) {
                            delay(200)
                            level = skill.level
                        }
                        val animatedCircularValue by animateFloatAsState(
                            targetValue = level,
                            label = "circular progress value",
                            animationSpec = keyframes {
                                this.durationMillis = 500
                            }
                        )
                        CircularProgressIndicator(
                            animatedCircularValue,
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
                    "My main skills are on Android, Kotlin and Java development. During my career, I wrote many articles about software development with topics like web API, mobile Apps, programing tips, refactoring etc",
                    skills = sampleSkills
                )
            }
        }
    }
}