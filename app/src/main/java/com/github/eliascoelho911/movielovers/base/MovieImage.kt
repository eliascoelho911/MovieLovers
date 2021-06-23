package com.github.eliascoelho911.movielovers.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.github.eliascoelho911.movielovers.tmdb.TMDBImage
import com.github.eliascoelho911.movielovers.tmdb.w780
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState

@Composable
fun MovieImage(
    path: String,
    contentDescription: String,
    modifier: Modifier
) {
    val painter = rememberCoilPainter(
        request = TMDBImage.getAbsoluteUrl(
            w780,
            path
        ),
        fadeIn = true
    )

    Box {
        when (painter.loadState) {
            is ImageLoadState.Success -> {
                Image(
                    modifier = modifier,
                    painter = painter,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
                )
            }
            else -> {
                Box(
                    modifier = modifier
                        .background(Color.LightGray)
                )
            }
        }
    }
}