package com.github.eliascoelho911.movielovers.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.ui.theme.MovieLoversTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint


internal const val KEY_ARG_MOVIE = "KEY_ARG_MOVIE"

fun launchMovieDetailsActivity(context: Context, movie: Movie) {
    context.startActivity(Intent(context, MovieDetailsActivity::class.java).apply {
        putExtra(KEY_ARG_MOVIE, movie)
    })
}

@AndroidEntryPoint
class MovieDetailsActivity : ComponentActivity() {

    private val movieDetailsViewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                MovieLoversTheme {
                    MovieDetailsScreen(
                        movieDetailsViewModel = movieDetailsViewModel,
                        onBackPressed = { finish() })
                }
            }
        }
    }
}