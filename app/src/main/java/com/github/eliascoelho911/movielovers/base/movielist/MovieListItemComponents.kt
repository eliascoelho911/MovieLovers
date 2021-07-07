package com.github.eliascoelho911.movielovers.base.movielist

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun BodyText(modifier: Modifier = Modifier, text: String, maxLines: Int = Int.MAX_VALUE) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.Gray,
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        style = MaterialTheme.typography.body2
    )
}

@Composable
fun TitleText(modifier: Modifier = Modifier, text: String) {
    Text(modifier = modifier, text = text, maxLines = 1, overflow = TextOverflow.Ellipsis)
}