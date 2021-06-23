package com.github.eliascoelho911.movielovers.base

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.ui.theme.Yellow
import java.math.RoundingMode

@Composable
fun MovieHorizontalList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    horizontalPadding: Dp,
    item: @Composable (position: Int, currentMovie: Movie) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        itemsIndexed(movies, itemContent = { position, currentMovie ->
            val paddingValues = when (position) {
                0 -> PaddingValues(start = horizontalPadding, end = 8.dp)
                movies.size - 1 -> PaddingValues(end = horizontalPadding)
                else -> PaddingValues(end = 8.dp)
            }
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                item(position = position, currentMovie = currentMovie)
            }
        })
    }
}

@Composable
fun MovieHorizontalListItem(
    title: String,
    path: String,
    voteAverage: Double,
) {
    Column(modifier = Modifier.itemModifier()) {
        MovieImage(
            path = path,
            contentDescription = title,
            modifier = Modifier.imageModifier()
        )
        TitleText(title)
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
                    text = voteAverage.toBigDecimal().setScale(1, RoundingMode.UP).toString(),
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun MovieHorizontalListItem(
    title: String,
    path: String,
    genre: String,
) {

    Column(modifier = Modifier.itemModifier()) {
        MovieImage(
            path = path,
            contentDescription = title,
            modifier = Modifier.imageModifier()
        )
        TitleText(title)
        ProvideTextStyle(value = MaterialTheme.typography.body2) {
            Text(
                text = genre,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun TitleText(title: String) {
    Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

private fun Modifier.imageModifier(): Modifier = composed {
    this
        .width(ItemWidth)
        .height(ImageHeight)
        .clip(MaterialTheme.shapes.medium)
}

private fun Modifier.itemModifier(): Modifier = composed {
    this
        .width(ItemWidth)
}

private val ItemWidth = 160.dp
private val ImageHeight = 230.dp

