package com.github.eliascoelho911.movielovers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.eliascoelho911.movielovers.ui.main.MainViewModel
import com.github.eliascoelho911.movielovers.ui.main.MoviesScreen
import com.github.eliascoelho911.movielovers.ui.theme.MovieLoversTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieLoversTheme {
                NavHost(navController = rememberNavController(), startDestination = "main") {
                    composable("main") { MoviesScreen(mainViewModel = mainViewModel) }
                }
            }
        }
    }
}