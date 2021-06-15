package com.github.eliascoelho911.movielovers.movies

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.components.CustomTextField
import com.github.eliascoelho911.movielovers.components.MovieHorizontalList
import com.github.eliascoelho911.movielovers.components.MovieLoversLogo
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import com.github.eliascoelho911.movielovers.ui.theme.DarkGray
import java.util.*

private val PADDING_SCREEN = 16.dp

@ExperimentalAnimationApi
@Composable
fun MoviesScreen(moviesViewModel: MoviesViewModel) {
    val popularMovies: List<Movie>? by moviesViewModel.popularMovies.observeAsState()

    Scaffold(topBar = { MoviesScreenTopBar() }) {
        MovieScreenContent(popularMovies)
    }
}

@Composable
private fun MovieScreenContent(popularMovies: List<Movie>?) {
    Column {
        ProvideTextStyle(value = MaterialTheme.typography.subtitle2) {
            Text(
                modifier = Modifier.padding(top = PADDING_SCREEN, start = PADDING_SCREEN),
                text = stringResource(id = R.string.popular_movies).uppercase(Locale.getDefault())
            )
        }
        if (popularMovies != null)
            MovieHorizontalList(
                modifier = Modifier.padding(top = PADDING_SCREEN),
                movies = popularMovies,
                paddingStart = PADDING_SCREEN
            )
    }
}

@ExperimentalAnimationApi
@Composable
@Preview
fun MoviesScreenTopBar() {
    var logoIsVisible by remember { mutableStateOf(true) }
    var searchedText by remember { mutableStateOf("") }
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AnimatedVisibility(
                    visible = !logoIsVisible,
                    enter = expandHorizontally(
                        expandFrom = Alignment.Start,
                        initialWidth = { 0 }
                    ) + fadeIn(initialAlpha = 0.3f),
                    exit = shrinkHorizontally(
                        shrinkTowards = Alignment.End
                    ) + shrinkVertically(shrinkTowards = Alignment.Bottom) + fadeOut()
                ) {
                    ProvideTextStyle(value = MaterialTheme.typography.body1) {
                        CustomTextField(
                            modifier = Modifier.fillMaxWidth(),
                            text = searchedText,
                            onValueChanged = { searchedText = it })
                    }
                }
                AnimatedVisibility(
                    visible = logoIsVisible,
                    enter = fadeIn(initialAlpha = 0.3f),
                    exit = fadeOut()
                ) {
                    MovieLoversLogo()
                }
            }
        },
        actions = {
            IconButton(onClick = { logoIsVisible = !logoIsVisible }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(
                        id = R.string.search
                    ),
                    tint = DarkGray
                )
            }
        }
    )
}