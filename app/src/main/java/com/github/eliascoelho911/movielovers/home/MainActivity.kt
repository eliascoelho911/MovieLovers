package com.github.eliascoelho911.movielovers.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import com.github.eliascoelho911.movielovers.listmovies.launchListMoviesActivity
import com.github.eliascoelho911.movielovers.tmdb.TmdbViewModel
import com.github.eliascoelho911.movielovers.ui.theme.MovieLoversTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val tmdbViewModel: TmdbViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieLoversTheme {
                HomeScreen(
                    tmdbViewModel = tmdbViewModel,
                    onClickShowAll = { movies ->
                        launchListMoviesActivity(
                            context = this,
                            movies = movies
                        )
                    })
            }
        }
    }
}