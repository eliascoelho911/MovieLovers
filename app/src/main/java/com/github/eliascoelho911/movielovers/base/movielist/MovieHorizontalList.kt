package com.github.eliascoelho911.movielovers.base.movielist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.base.MovieImage
import com.github.eliascoelho911.movielovers.model.Movie

@Composable
fun MovieHorizontalList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    item: @Composable (position: Int, currentMovie: Movie) -> Unit
) {
    LazyRow(modifier = modifier) {
        itemsIndexed(movies, itemContent = { position, currentMovie ->
            item(position = position, currentMovie = currentMovie)
        })
    }
}

@Composable
fun MovieVerticalList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    item: @Composable (position: Int, currentMovie: Movie) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(movies, itemContent = { position, currentMovie ->
            item(position = position, currentMovie = currentMovie)
        })
    }
}

@Composable
fun MovieHorizontalListItem(
    title: String,
    pathImage: String,
    voteAverage: Double,
) {
    Column(modifier = Modifier.itemModifier()) {
        MovieImage(
            path = pathImage,
            contentDescription = title,
            modifier = Modifier.imageModifier()
        )
        TitleText(text = title)
        VoteAverage(voteAverage = voteAverage)
    }
}

@Composable
fun MovieHorizontalListItem(
    title: String,
    pathImage: String,
    genre: String,
) {
    Column(modifier = Modifier.itemModifier()) {
        MovieImage(
            path = pathImage,
            contentDescription = title,
            modifier = Modifier.imageModifier()
        )
        TitleText(text = title)
        BodyText(text = genre, maxLines = 1)
    }
}

private fun Modifier.imageModifier(): Modifier = composed {
    this
        .width(itemWidth)
        .height(imageHeight)
        .clip(MaterialTheme.shapes.medium)
}

private fun Modifier.itemModifier(): Modifier = composed {
    this
        .width(itemWidth)
}

private val itemWidth = 160.dp
private val imageHeight = 230.dp

