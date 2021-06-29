package com.github.eliascoelho911.movielovers.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.github.eliascoelho911.movielovers.listmovies.launchListMoviesActivity
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.tmdb.TmdbViewModel
import com.github.eliascoelho911.movielovers.ui.theme.MovieLoversTheme
import com.google.accompanist.insets.ProvideWindowInsets
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val tmdbViewModel: TmdbViewModel by viewModels()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ProvideWindowInsets {
                MovieLoversTheme {
                    MainActivityScreen(
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
}

@ExperimentalAnimationApi
@VisibleForTesting
@Composable
fun MainActivityScreen(tmdbViewModel: TmdbViewModel, onClickShowAll: (List<Movie>) -> Unit) {
    Surface(color = MaterialTheme.colors.primary) {
        val transitionState = remember { MutableTransitionState(SplashState.Shown) }
        val transition = updateTransition(transitionState, label = "splashTransition")
        val splashAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 100) }, label = "splashAlpha"
        ) {
            if (it == SplashState.Shown) 1f else 0f
        }
        val contentAlpha by transition.animateFloat(
            transitionSpec = { tween(durationMillis = 600) }, label = "contentAlpha"
        ) {
            if (it == SplashState.Shown) 0f else 1f
        }
        val contentTopPadding by transition.animateDp(
            transitionSpec = { spring(stiffness = Spring.StiffnessLow) },
            label = "contentTopPadding"
        ) {
            if (it == SplashState.Shown) 100.dp else 0.dp
        }

        Box {
            LandingScreen(
                modifier = Modifier.alpha(splashAlpha),
                onTimeout = { transitionState.targetState = SplashState.Completed }
            )
            MainActivityContent(
                modifier = Modifier.alpha(contentAlpha),
                topPadding = contentTopPadding,
                tmdbViewModel = tmdbViewModel,
                onClickShowAll = onClickShowAll
            )
        }
    }
}

@ExperimentalAnimationApi
@Composable
private fun MainActivityContent(
    modifier: Modifier = Modifier,
    topPadding: Dp = 0.dp,
    tmdbViewModel: TmdbViewModel,
    onClickShowAll: (List<Movie>) -> Unit
) {
    Column(modifier = modifier) {
        Spacer(Modifier.padding(top = topPadding))
        HomeScreen(
            modifier = modifier,
            tmdbViewModel = tmdbViewModel,
            onClickShowAll = onClickShowAll
        )
    }
}

enum class SplashState { Shown, Completed }