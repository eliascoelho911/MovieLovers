package com.github.eliascoelho911.movielovers.ui.components

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.eliascoelho911.movielovers.retrofit.data.Movie

@Composable
fun MovieHorizontalList(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    item: @Composable (position: Int, currentMovie: Movie) -> Unit
) {
    LazyRow(
        modifier = modifier
    ) {
        itemsIndexed(movies, itemContent = { position, currentMovie ->
            item(position = position, currentMovie = currentMovie)
        })
    }
}