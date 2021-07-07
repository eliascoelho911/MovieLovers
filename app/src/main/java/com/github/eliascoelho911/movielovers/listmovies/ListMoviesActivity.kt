package com.github.eliascoelho911.movielovers.listmovies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.core.view.WindowCompat
import com.github.eliascoelho911.movielovers.details.launchMovieDetailsActivity
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.ui.theme.MovieLoversTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

internal const val KEY_ARG_MOVIES = "KEY_ARG_MOVIES"

fun launchListMoviesActivity(context: Context, movies: List<Movie>) {
    context.startActivity(Intent(context, ListMoviesActivity::class.java).apply {
        putParcelableArrayListExtra(KEY_ARG_MOVIES, ArrayList(movies))
    })
}

@AndroidEntryPoint
class ListMoviesActivity : ComponentActivity() {

    private val listMoviesViewModel: ListMoviesViewModel by viewModels()

    @ExperimentalFoundationApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                MovieLoversTheme {
                    ListMoviesScreen(
                        listMoviesViewModel = listMoviesViewModel,
                        onBackPressed = { finish() },
                        onClickMovieItem = { movie ->
                            launchMovieDetailsActivity(
                                context = this,
                                movie = movie
                            )
                        }
                    )
                }
            }
        }
    }
}