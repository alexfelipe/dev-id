package br.com.alexfelipe.devid.samples

import br.com.alexfelipe.devid.models.Skill
import kotlin.random.Random

val sampleSkills = listOf(
    Skill(name = "Android", level = Random.nextFloat()),
    Skill(name = "Kotlin", level = Random.nextFloat()),
    Skill(name = "Java", level = Random.nextFloat()),
    Skill(name = "Spring framework", level = Random.nextFloat()),
    Skill(name = "Git", level = Random.nextFloat())
)
