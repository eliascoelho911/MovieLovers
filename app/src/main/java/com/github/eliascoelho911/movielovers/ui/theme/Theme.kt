package com.github.eliascoelho911.movielovers.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = Color.LightGray,
    secondary = Red,
    secondaryVariant = DarkRed,
    background = Color.White,
    surface = Color.White,
    error = Red,
    onPrimary = DarkGray,
    onSecondary = DarkGray,
    onSurface = DarkGray,
    onError = Color.White
)

@Composable
fun MovieLoversTheme(
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color.White)

    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}