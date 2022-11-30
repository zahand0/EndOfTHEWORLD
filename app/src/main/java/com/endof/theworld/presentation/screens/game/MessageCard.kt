package com.endof.theworld.presentation.screens.game

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.endof.theworld.R


@Composable
fun LoseMessageCard(
    @DrawableRes
    imageId: Int,
    onClick: () -> Unit,
    alpha: Float
) {
    MessageCard(
        imageId = imageId,
        onClick = onClick,
        title = stringResource(R.string.you_defeated),
        buttonText = stringResource(R.string.restart),
        alpha = alpha
    )
}

@Composable
fun WinMessageCard(
    enemyName: String,
    @DrawableRes
    imageId: Int,
    onClick: () -> Unit,
    alpha: Float
) {
    MessageCard(
        imageId = imageId,
        onClick = onClick,
        title = stringResource(R.string.enemy_was_defeated).format(enemyName),
        buttonText = stringResource(R.string.continue_text),
        alpha = alpha
    )
}

@Composable
fun GameEndMessageCard(
    enemyName: String,
    @DrawableRes
    imageId: Int,
    onClick: () -> Unit,
    alpha: Float
) {
    MessageCard(
        imageId = imageId,
        onClick = onClick,
        title = stringResource(R.string.game_end_message).format(enemyName),
        buttonText = stringResource(R.string.menu),
        alpha = alpha
    )
}

@Composable
fun MessageCard(
    @DrawableRes
    imageId: Int,
    onClick: () -> Unit,
    title: String,
    buttonText: String,
    alpha: Float
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
            .alpha(alpha),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            backgroundColor = MaterialTheme.colors.background,
            border = BorderStroke(2.dp, MaterialTheme.colors.primaryVariant)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(16.dp)
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h4,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    modifier = Modifier
                        .height(200.dp),
                    contentScale = ContentScale.FillHeight,
                    painter = painterResource(id = imageId),
                    contentDescription = stringResource(R.string.message_image)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    modifier = Modifier.width(200.dp),
                    onClick = {
                        onClick()
                    },
                    shape = CircleShape
                ) {
                    Text(
                        text = buttonText,
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colors.onPrimary
                    )
                }

            }
        }
    }
}