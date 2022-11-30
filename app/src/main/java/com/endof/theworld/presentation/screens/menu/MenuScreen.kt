package com.endof.theworld.presentation.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endof.theworld.R
import com.endof.theworld.domain.model.GameLevel
import com.endof.theworld.presentation.navigation.Screen
import com.endof.theworld.util.Constants

@Composable
fun MenuScreen(
    navController: NavHostController,
    viewModel: MenuScreenViewModel = hiltViewModel()
) {
    val level by viewModel.lastLevel.collectAsState(initial = GameLevel.ONE)
    val isMusicEnabled by viewModel.isMusicEnabled().collectAsState(initial = true)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .scrollable(rememberScrollState(), Orientation.Vertical),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                modifier = Modifier.height(300.dp),
                painter = painterResource(id = R.drawable.background_dio),
                contentScale = ContentScale.FillHeight,
                contentDescription = stringResource(R.string.background_image)
            )
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground,
            )
            Spacer(modifier = Modifier.height(16.dp))


            if (level != GameLevel.ONE) {
                MenuButton(
                    modifier = Modifier.width(240.dp),
                    text = "Load Game"
                ) {

                    navController.navigate(Screen.Game.route)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            MenuButton(
                modifier = Modifier.width(240.dp),
                text = "New Game"
            ) {
                viewModel.updateRemainHeal(Constants.HEAL_MAX_NUMBER)
                viewModel.updateLevel(GameLevel.ONE)
                navController.navigate(Screen.Game.route)
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(onClick = { viewModel.setMusicSettings(!isMusicEnabled) }) {
                val iconId =
                    if (isMusicEnabled) R.drawable.ic_music_on else R.drawable.ic_music_off
                Icon(
                    imageVector = ImageVector.vectorResource(
                        id = iconId
                    ),
                    contentDescription = null,
                    tint = if (isMusicEnabled) MaterialTheme.colors.onSurface else Color.Gray
                )
            }
        }
    }
}


@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = {
            onClick()
        },
        shape = CircleShape
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colors.onPrimary
        )
    }
}