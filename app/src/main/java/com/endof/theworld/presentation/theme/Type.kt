package com.endof.theworld.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.endof.theworld.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily(Font(R.font.retro_gaming)),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    // Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily(Font(R.font.retro_gaming)),
        fontWeight = FontWeight.W500,
        fontSize = 18.sp
    ),
    h3 = TextStyle(
        fontFamily = FontFamily(Font(R.font.retro_gaming)),
        fontWeight = FontWeight.W600,
        fontSize = 48.sp
    ),
    h4 = TextStyle(
        fontFamily = FontFamily(Font(R.font.retro_gaming)),
        fontWeight = FontWeight.W600,
        fontSize = 34.sp
    ),
    h5 = TextStyle(
        fontFamily = FontFamily(Font(R.font.retro_gaming)),
        fontWeight = FontWeight.W600,
        fontSize = 20.sp
    ),
    h6 = TextStyle(
        fontFamily = FontFamily(Font(R.font.retro_gaming)),
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    )
//    caption = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontWeight = FontWeight.Normal,
//        fontSize = 12.sp
//    )
)