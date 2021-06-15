package com.github.eliascoelho911.movielovers.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.retrofit.data.Movie

@Composable
fun MovieHorizontalList(
    modifier: Modifier = Modifier,
    modifierItem: Modifier = Modifier,
    movies: List<Movie>,
    paddingStart: Dp
) {
    LazyRow(
        modifier = modifier
    ) {
        itemsIndexed(movies, itemContent = { position, currentMovie ->
            MovieHorizontalListItem(
                modifier = modifierItem,
                title = currentMovie.title,
                path = currentMovie.posterPath,
                position = position,
                listSize = movies.size,
                paddingStart = paddingStart,
                voteAverage = currentMovie.voteAverage
            )
        })
    }
}

@Composable
fun MovieHorizontalListItem(
    modifier: Modifier = Modifier,
    title: String,
    path: String,
    position: Int,
    listSize: Int,
    paddingStart: Dp,
    voteAverage: Double
) {
    val paddingValues = when (position) {
        0 -> PaddingValues(start = paddingStart, end = 8.dp)
        listSize - 1 -> PaddingValues(all = 0.dp)
        else -> PaddingValues(end = 8.dp)
    }
    MoviePoster(modifier = modifier, title = title, path = path, paddingValues = paddingValues, voteAverage = voteAverage)
}