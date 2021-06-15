package com.github.eliascoelho911.movielovers.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.ui.theme.Yellow
import com.github.eliascoelho911.movielovers.util.TMDBImage
import com.github.eliascoelho911.movielovers.util.w780
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import java.math.RoundingMode.UP

@Composable
fun MoviePoster(
    modifier: Modifier = Modifier,
    title: String,
    path: String,
    voteAverage: Double,
    paddingValues: PaddingValues
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
        .padding(paddingValues)
        .width(width)
        .height(230.dp)
        .clip(MaterialTheme.shapes.medium)

    Box {
        Column {
            Image(
                modifier = imageModifier,
                painter = painter, contentDescription = title
            )

            if (painter.loadState is ImageLoadState.Success) {
                Spacer(modifier = Modifier.padding(top = 8.dp))
                Column(
                    modifier = Modifier
                        .width(width)
                        .padding(paddingValues)
                ) {
                    Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = stringResource(id = R.string.popularity),
                            tint = Yellow
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        ProvideTextStyle(value = MaterialTheme.typography.body2) {
                            Text(text = voteAverage.toBigDecimal().setScale(1, UP).toString())
                        }
                    }
                }
            }
        }
        if (painter.loadState is ImageLoadState.Loading) {
            Box(
                modifier = imageModifier
                    .width(width)
                    .background(Color.LightGray)
            )
        }
    }
}