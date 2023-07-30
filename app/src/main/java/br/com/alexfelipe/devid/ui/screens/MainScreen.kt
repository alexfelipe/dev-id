package br.com.alexfelipe.devid.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.alexfelipe.devid.ui.theme.DevIdTheme
import coil.compose.AsyncImage

@Composable
fun MainScreen(
    profilePic: String,
    userName: String,
    bio: String,
    skills: List<String> = listOf()
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = profilePic,
                contentDescription = "profile pic",
                Modifier
                    .clip(CircleShape)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
        Text(text = userName)
        Text(text = bio)
        Text(text = "My skills")
        for (skill in skills) {
            Text(text = skill)
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
            MainScreen(
                "https://avatars.githubusercontent.com/u/8989346?v=4",
                "Alex Felipe",
                "I'm a software developer, so let's start the code"
            )
        }
    }
}