package com.github.eliascoelho911.movielovers.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.tmdb.TMDBImage
import com.github.eliascoelho911.movielovers.tmdb.w780
import com.github.eliascoelho911.movielovers.ui.theme.Yellow
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import java.math.RoundingMode.UP

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    title: String,
    path: String,
    voteAverage: Double,
) {
    MoviePosterImpl(
        title = title,
        path = path,
        modifier = modifier,
        subtitle = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(id = R.string.popularity),
                    tint = Yellow
                )
                ProvideTextStyle(value = MaterialTheme.typography.body2) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = voteAverage.toBigDecimal().setScale(1, UP).toString(),
                        color = Color.Gray
                    )
                }
            }
        })
}

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    title: String,
    path: String,
    genre: String,
) {
    MoviePosterImpl(
        title = title,
        path = path,
        modifier = modifier,
        subtitle = {
            ProvideTextStyle(value = MaterialTheme.typography.body2) {
                Text(
                    text = genre,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        })
}

@Composable
private fun MoviePosterImpl(
    modifier: Modifier = Modifier,
    title: String,
    path: String,
    subtitle: @Composable () -> Unit,
) {
    val painter = rememberCoilPainter(
        request = TMDBImage.getAbsoluteUrl(
            w780,
            path
        ),
        fadeIn = true
    )
    val width = 160.dp
    val imageModifier = modifier
        .width(width)
        .height(230.dp)
        .clip(MaterialTheme.shapes.medium)

    Box {
        Column {
            Image(
                modifier = imageModifier,
                painter = painter, contentDescription = title,
                contentScale = ContentScale.Crop
            )

            if (painter.loadState is ImageLoadState.Success) {
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Column(
                    modifier = Modifier
                        .width(width)
                ) {
                    Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    subtitle()
                }
            }
        }
        if (painter.loadState is ImageLoadState.Loading || painter.loadState is ImageLoadState.Error) {
            Box(
                modifier = imageModifier
                    .width(width)
                    .background(Color.LightGray)
            )
        }
    }
}