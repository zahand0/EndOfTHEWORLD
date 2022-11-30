package com.endof.theworld.presentation.screens.game

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.endof.theworld.domain.model.GameLevel
import com.endof.theworld.domain.model.GameResult
import com.endof.theworld.domain.model.nextLevel
import com.endof.theworld.presentation.common.TopBar
import com.endof.theworld.presentation.util.imgId
import com.endof.theworld.presentation.util.imgLoseId
import com.endof.theworld.presentation.util.name
import com.endof.theworld.presentation.util.soundWinId
import com.endof.theworld.util.Constants


@Composable
fun GameScreen(
    navController: NavHostController,
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val level by viewModel.lastLevel.collectAsState(initial = null)
    val remainHeal by viewModel.remainHeal.collectAsState(initial = null)
    LaunchedEffect(key1 = level) {
        level?.let { gameLevel ->
            remainHeal?.let { heal ->
                if (!viewModel.gameStarted) {
                    viewModel.start(
                        level = gameLevel,
                        healCount = heal
                    )
                }
            }
        }
    }

    val player by viewModel.player.collectAsState()
    val enemy by viewModel.enemy.collectAsState()
    val gameResult by viewModel.gameResult.collectAsState()
    val gameState by viewModel.gameState.collectAsState()
    val enemyMsg by viewModel.enemyMsg.collectAsState(initial = "")
    val playerMsg by viewModel.playerMsg.collectAsState(initial = "")

    var state by remember {
        mutableStateOf(true)
    }
    val firstTimeSoundPopup = remember {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = gameResult) {
        if (gameResult != GameResult.PLAYING &&
            !firstTimeSoundPopup.value
        ) {
            viewModel.playSound(
                soundId = if (gameResult == GameResult.WIN)
                    player.soundWinId()
                else
                    enemy.soundWinId()
            )
        } else {
            firstTimeSoundPopup.value = false
        }
    }

    val alphaState by animateFloatAsState(
        targetValue = if (state) 1f else 0f,
        animationSpec = tween(
            delayMillis = 250,
            durationMillis = 300
        )
    )

    Scaffold(
        topBar = {
            TopBar(
                title = ""
            ) {
                navController.popBackStack()
            }
        },
        backgroundColor = MaterialTheme.colors.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            Battle(
                enemyImgRes = enemy.imgId(),
                enemyName = enemy.name(),
                enemyMaxHp = enemy.stats.maxHealth,
                playerImgRes = player.imgId(),
                playerName = player.name(),
                playerMaxHp = player.stats.maxHealth,
                onAttackClick = viewModel::playerAttack,
                onHealClick = viewModel::playerHeal,
                isLoading = level == null,
                enemyMsg = enemyMsg,
                playerMsg = playerMsg,
                gameState = gameState,
                gameResult = gameResult
            )
            when (gameResult) {
                GameResult.PLAYING -> {
                    state = false
                }
                GameResult.LOSE -> {
                    state = true
                    LoseMessageCard(
                        imageId = player.imgLoseId(),
                        onClick = {

                            viewModel.start(
                                level = GameLevel.ONE,
                                healCount = Constants.HEAL_MAX_NUMBER
                            )
                            viewModel.updateLevel(GameLevel.ONE)
                        },
                        alpha = alphaState
                    )
                }
                GameResult.WIN -> {
                    state = true
                    if (level == GameLevel.lastLevel()) {
                        GameEndMessageCard(
                            enemyName = enemy.name(),
                            imageId = enemy.imgLoseId(),
                            onClick = {
                                viewModel.updateRemainHeal(Constants.HEAL_MAX_NUMBER)
                                level?.let { gameLevel ->
                                    viewModel.updateLevel(gameLevel.nextLevel())
                                    navController.popBackStack()
                                }
                            },
                            alpha = alphaState
                        )
                    } else {
                        WinMessageCard(
                            enemyName = enemy.name(),
                            imageId = enemy.imgLoseId(),
                            onClick = {
                                viewModel.updateRemainHeal(gameState.healCount)
                                level?.let { gameLevel ->
                                    viewModel.start(
                                        level = gameLevel.nextLevel(),
                                        healCount = gameState.healCount
                                    )
                                    viewModel.updateLevel(gameLevel.nextLevel())
                                }
                            },
                            alpha = alphaState
                        )
                    }
                }
            }
        }
    }
}



