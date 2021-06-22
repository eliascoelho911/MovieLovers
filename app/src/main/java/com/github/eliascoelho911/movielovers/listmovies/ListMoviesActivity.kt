package com.github.eliascoelho911.movielovers.listmovies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import com.github.eliascoelho911.movielovers.model.Movie
import dagger.hilt.android.AndroidEntryPoint

internal const val KEY_ARG_MOVIES = "KEY_ARG_MOVIES"

fun launchListMoviesActivity(context: Context, movies: List<Movie>) {
    context.startActivity(Intent(context, ListMoviesActivity::class.java).apply {
        putExtra(KEY_ARG_MOVIES, movies.toTypedArray())
    })
}

@AndroidEntryPoint
class ListMoviesActivity : ComponentActivity() {

    private val listMoviesViewModel: ListMoviesViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ListMoviesScreen(listMoviesViewModel)
        }
    }
}