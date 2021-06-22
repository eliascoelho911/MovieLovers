package com.github.eliascoelho911.movielovers.listmovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.github.eliascoelho911.movielovers.base.MovieLoversLogo

@ExperimentalAnimationApi
@Composable
fun ListMoviesScreen(listMoviesViewModel: ListMoviesViewModel) {
    Scaffold(topBar = { TopAppBar(title = { MovieLoversLogo() }) }) {
        LazyColumn {
            items(listMoviesViewModel.movies) { movie ->
                Text(text = movie.title)
            }
        }
    }
}