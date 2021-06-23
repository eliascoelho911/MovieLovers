package com.github.eliascoelho911.movielovers.listmovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.github.eliascoelho911.movielovers.base.MovieLoversLogo

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun ListMoviesScreen(listMoviesViewModel: ListMoviesViewModel) {
    Scaffold(topBar = { TopAppBar(title = { MovieLoversLogo() }) }) {
        LazyVerticalGrid(cells = GridCells.Fixed(2)) {
            items(listMoviesViewModel.movies) { movie ->
//                MoviePoster(title = movie.title, path = movie.posterPath ?: "", )
            }
        }
    }
}