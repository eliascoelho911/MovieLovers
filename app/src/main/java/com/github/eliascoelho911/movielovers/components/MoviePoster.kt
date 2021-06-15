package com.github.eliascoelho911.movielovers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.util.TMDBImage
import com.github.eliascoelho911.movielovers.util.w780
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    title: String,
    path: String,
    paddingValues: PaddingValues
) {
    val painter = rememberCoilPainter(
        request = TMDBImage.getAbsoluteUrl(
            w780,
            path
        ),
        fadeIn = true
    )
    val newModifier = modifier
        .padding(paddingValues)
        .height(230.dp)
        .clip(MaterialTheme.shapes.medium)

    Image(
        modifier = newModifier,
        painter = painter, contentDescription = title
    )

    if (painter.loadState is ImageLoadState.Loading) {
        Box(
            modifier = newModifier
                .width(160.dp)
                .background(Color.LightGray)
        )
    }
}