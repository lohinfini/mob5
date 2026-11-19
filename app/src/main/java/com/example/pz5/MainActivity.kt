
package com.example.sportapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SportApp()
        }
    }
}

private val Green = Color(0xFF35C98B)
private val Dark = Color(0xFF101B25)

private val AppTypography = Typography(
    titleLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 25.sp,
        fontWeight = FontWeight.Bold
    ),
    titleMedium = androidx.compose.ui.text.TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = androidx.compose.ui.text.TextStyle(
        fontSize = 16.sp
    ),
    bodyMedium = androidx.compose.ui.text.TextStyle(
        fontSize = 14.sp
    )
)

data class Workout(
    val name: String,
    val category: String,
    val duration: String,
    val icon: String,
    val description: String
)

private val workouts = listOf(
    Workout(
        "Бег",
        "Кардиотренировка",
        "30 минут",
        "🏃",
        "Бег помогает развивать выносливость и укреплять сердечно-сосудистую систему."
    ),
    Workout(
        "Силовая тренировка",
        "Работа с мышцами",
        "45 минут",
        "🏋️",
        "Комплекс упражнений для развития силы и укрепления основных групп мышц."
    ),
    Workout(
        "Йога",
        "Растяжка и баланс",
        "25 минут",
        "🧘",
        "Спокойная тренировка для гибкости, равновесия и расслабления."
    ),
    Workout(
        "Велотренировка",
        "Кардиотренировка",
        "40 минут",
        "🚴",
        "Велосипедная тренировка для развития выносливости."
    )
)

@Composable
fun SportApp() {
    var darkTheme by remember {
        mutableStateOf(true)
    }

    SportAppTheme(darkTheme = darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SportScreen(
                darkTheme = darkTheme,
                onThemeChange = { darkTheme = it }
            )
        }
    }
}

@Composable
fun SportAppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Green,
            secondary = Color(0xFF82DDB5),
            background = Dark,
            surface = Color(0xFF1C2A36),
            onPrimary = Dark,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF168A60),
            secondary = Green,
            background = Color(0xFFF2F7F4),
            surface = Color.White,
            onPrimary = Color.White,
            onBackground = Color(0xFF17251F),
            onSurface = Color(0xFF17251F)
        )
    }

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content
    )
}

@Composable
fun SportScreen(
    darkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val isTablet = maxWidth >= 700.dp

        if (isTablet) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1.5f)
                ) {
                    Header()
                    Spacer(Modifier.height(20.dp))
                    Statistics()
                    Spacer(Modifier.height(20.dp))

                    Text(
                        "Мои тренировки",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(Modifier.height(12.dp))

                    WorkoutList(
                        modifier = Modifier.weight(1f)
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SettingsCard(
                        darkTheme,
                        onThemeChange
                    )
                    TipsCard()
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 18.dp),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Header()
                }

                item {
                    Statistics()
                }

                item {
                    SettingsCard(
                        darkTheme,
                        onThemeChange
                    )
                }

                item {
                    Text(
                        "Мои тренировки",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(workouts) { workout ->
                    WorkoutCard(workout)
                }

                item {
                    TipsCard()
                }
            }
        }
    }
}

@Composable
fun Header() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(55.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "S",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "SportApp",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Твой путь к активности"
            )
        }
    }
}

@Composable
fun Statistics() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            "Тренировки",
            "12",
            "🏆",
            Modifier.weight(1f)
        )

        StatCard(
            "Минут",
            "480",
            "⏱",
            Modifier.weight(1f)
        )

        StatCard(
            "Достижения",
            "5",
            "⭐",
            Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(icon, fontSize = 23.sp)

            Text(
                value,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                title,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.7f
                )
            )
        }
    }
}

@Composable
fun WorkoutList(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(workouts) { workout ->
            WorkoutCard(workout)
        }
    }
}

@Composable
fun WorkoutCard(workout: Workout) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val cardColor by animateColorAsState(
        targetValue = if (expanded) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "cardColor"
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .clickable {
                expanded = !expanded
            },
        shape = RoundedCornerShape(18.dp),
        color = cardColor,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    workout.icon,
                    fontSize = 35.sp
                )

                Spacer(Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        workout.name,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        workout.category,
                        color = MaterialTheme.colorScheme.onSurface.copy(
                            alpha = 0.65f
                        ),
                        fontSize = 13.sp
                    )
                }

                Text(
                    workout.duration,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            if (expanded) {
                Spacer(Modifier.height(14.dp))

                HorizontalDivider()

                Spacer(Modifier.height(10.dp))

                Text(
                    workout.description,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    "Нажми ещё раз, чтобы свернуть",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun SettingsCard(
    darkTheme: Boolean,
    onChange: (Boolean) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Тёмная тема",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Изменить оформление",
                    fontSize = 12.sp
                )
            }

            Switch(
                checked = darkTheme,
                onCheckedChange = onChange
            )
        }
    }
}

@Composable
fun TipsCard() {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                "Совет дня",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Не забывай делать разминку перед тренировкой " +
                        "и соблюдать комфортный темп занятий."
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 850
)
@Composable
fun PhoneLightPreview() {
    SportAppTheme(darkTheme = false) {
        Surface {
            SportScreen(
                darkTheme = false,
                onThemeChange = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 390,
    heightDp = 850,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PhoneDarkPreview() {
    SportAppTheme(darkTheme = true) {
        Surface {
            SportScreen(
                darkTheme = true,
                onThemeChange = {}
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 1000
)
@Composable
fun TabletPreview() {
    SportAppTheme(darkTheme = false) {
        Surface {
            SportScreen(
                darkTheme = false,
                onThemeChange = {}
            )
        }
    }
}
