package com.github.eliascoelho911.movielovers.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.model.Movie

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