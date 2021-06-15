package com.github.eliascoelho911.movielovers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.eliascoelho911.movielovers.movies.MoviesScreen
import com.github.eliascoelho911.movielovers.movies.MoviesViewModel
import com.github.eliascoelho911.movielovers.ui.theme.MovieLoversTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val moviesViewModel: MoviesViewModel by viewModels()

    @ExperimentalPagerApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieLoversTheme {
                NavHost(navController = rememberNavController(), startDestination = "movies") {
                    composable("movies") { MoviesScreen(moviesViewModel = moviesViewModel) }
                }
            }
        }
    }
}