package com.endof.theworld.presentation.screens.game

import androidx.annotation.DrawableRes
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.endof.theworld.R
import com.endof.theworld.domain.model.GameResult
import com.endof.theworld.domain.model.GameState
import com.endof.theworld.util.Constants
import kotlinx.coroutines.delay
import kotlin.math.min


@Composable
fun Battle(
    @DrawableRes
    enemyImgRes: Int,
    enemyName: String,
    enemyMaxHp: Int,
    enemyMsg: String,
    @DrawableRes
    playerImgRes: Int,
    playerName: String,
    playerMaxHp: Int,
    playerMsg: String,
    onAttackClick: () -> Unit,
    onHealClick: () -> Unit,
    isLoading: Boolean,
    gameState: GameState,
    gameResult: GameResult
) {
    var enemyMsgVisible by remember {
        mutableStateOf(false)
    }
    var playerMsgVisible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = enemyMsg) {
        if (enemyMsg != "") {
            enemyMsgVisible = true
            delay(400)
            enemyMsgVisible = false
        }
    }
    LaunchedEffect(key1 = playerMsg) {
        if (playerMsg != "") {
            playerMsgVisible = true
            delay(400)
            playerMsgVisible = false
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.backgroung_gradient),
            contentDescription = stringResource(
                R.string.background
            ),
            contentScale = ContentScale.Crop,
            alpha = if (MaterialTheme.colors.isLight) 0.1f else 0.9f
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedVisibility(
                visible = !isLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        AnimatedVisibility(
                            visible = enemyMsgVisible,
                            enter = slideInVertically {
                                it / 2
                            },
                            exit = slideOutVertically {
                                -it / 2
                            } + fadeOut()
                        ) {
                            Text(
                                text = enemyMsg,
                                style = MaterialTheme.typography.h6,
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Image(
                            modifier = Modifier
//                                    .align(Alignment.End)
                                .height(180.dp),
                            contentScale = ContentScale.FillHeight,
                            painter = painterResource(id = enemyImgRes),
                            contentDescription = stringResource(R.string.enemy_image)
                        )
                    }
                    Card(
                        modifier = Modifier
                            .align(Alignment.End)
                            .fillMaxWidth(0.8f)
                            .height(60.dp),
                        border = BorderStroke(width = 2.dp, color = Color.Black),
                        backgroundColor = MaterialTheme.colors.secondary
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            BoxWithConstraints(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val hpBarWidth = maxWidth / 2
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = enemyName,
                                        style = MaterialTheme.typography.h5
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    HealthBar(
                                        modifier = Modifier
                                            .width(hpBarWidth),
                                        maxHP = enemyMaxHp,
                                        currentHP = gameState.enemyHp
                                    )

                                }
                            }

                        }
                    }
                }
            }


            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Image(
                    modifier = Modifier
                        .height(200.dp)
                        .offset(y = 8.dp),
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(id = playerImgRes),
                    contentDescription = stringResource(R.string.enemy_image)
                )
                Spacer(modifier = Modifier.width(16.dp))
                AnimatedVisibility(
                    visible = playerMsgVisible,
                    enter = slideInVertically {
                        it / 2
                    },
                    exit = slideOutVertically {
                        -it / 2
                    } + fadeOut()
                ) {
                    Text(
                        text = playerMsg,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
                border = BorderStroke(width = 2.dp, color = Color.Black),
                backgroundColor = MaterialTheme.colors.secondary
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    BoxWithConstraints(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        val hpBarWidth = maxWidth / 2
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = playerName,
                                style = MaterialTheme.typography.h5
                            )

                            HealthBar(
                                modifier = Modifier
                                    .width(hpBarWidth),
//                            .align(Alignment.End),
                                maxHP = playerMaxHp,
                                currentHP = gameState.playerHp
                            )

                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        BattleButton(
                            name = stringResource(R.string.attack),
                            onClick = onAttackClick,
                            enabled = gameState.playerTurn &&
                                    gameResult == GameResult.PLAYING
                        )
                        Spacer(modifier = Modifier.fillMaxWidth(0.2f))
                        BattleButton(
                            name = stringResource(R.string.heal).format(
                                gameState.healCount,
                                Constants.HEAL_MAX_NUMBER
                            ),
                            onClick = onHealClick,
                            enabled = gameState.playerTurn &&
                                    gameState.healCount > 0 &&
                                    gameResult == GameResult.PLAYING
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun HealthBar(
    modifier: Modifier = Modifier,
    maxHP: Int,
    currentHP: Int
) {
    Row(
        modifier = modifier.height(30.dp),
        verticalAlignment = Alignment.CenterVertically
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = stringResource(R.string.hp),
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.width(8.dp))
        var hpRatio = 1f
        if (currentHP <= 0) {
            hpRatio = 0f
        } else {
            if (maxHP > 0) {
                hpRatio = min(1f, currentHP / maxHP.toFloat())
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = Color.Black
                    ),
                    shape = MaterialTheme.shapes.small
                )
                .clip(MaterialTheme.shapes.small)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(
                        color = Color.Red,
                        shape = MaterialTheme.shapes.small
                    )
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            color = Color.Black
                        ),
                        shape = MaterialTheme.shapes.small
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(hpRatio)
                    .fillMaxHeight()
                    .background(
                        color = Color.Green,
                        shape = MaterialTheme.shapes.small
                    )
                    .border(
                        BorderStroke(
                            width = 1.dp,
                            color = Color.Black
                        ),
                        shape = MaterialTheme.shapes.small
                    )
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (maxHP > 0 && currentHP in 0..maxHP) {
                    Text(
                        text = "$currentHP/$maxHP",
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }

    }
}

@Composable
fun BattleButton(
    modifier: Modifier = Modifier,
    name: String,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Button(
        modifier = modifier
            .width(140.dp)
            .height(40.dp),
        shape = MaterialTheme.shapes.medium,
        border = BorderStroke(width = 2.dp, color = Color.Black),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(
            text = name
        )
    }
}
//
//@Preview
//@Composable
//fun BattlePreview() {
//    EndOfTHEWORLDTheme() {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colors.background
//        ) {
//            Battle(
//                enemyImgRes = R.drawable.dio,
//                enemyName = "Dio",
//                enemyMaxHp = 120,
//                enemyCurrentHp = 90,
//                enemyMsg = "-50",
//                playerImgRes = R.drawable.jotaro,
//                playerName = "Jotaro",
//                playerMaxHp = 100,
//                playerCurrentHp = 80,
//                playerMsg = "-10",
//                onAttackClick = {},
//                onHealClick = {},
//                isLoading = false
//            )
//        }
//    }
//}
