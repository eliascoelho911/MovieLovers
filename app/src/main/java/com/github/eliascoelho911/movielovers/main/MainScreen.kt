package com.github.eliascoelho911.movielovers.main

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.components.CustomTextField
import com.github.eliascoelho911.movielovers.components.MovieHorizontalList
import com.github.eliascoelho911.movielovers.components.MovieLoversLogo
import com.github.eliascoelho911.movielovers.components.MoviePoster
import com.github.eliascoelho911.movielovers.retrofit.data.Genre
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import com.github.eliascoelho911.movielovers.ui.theme.DarkGray
import com.github.eliascoelho911.movielovers.ui.theme.Green

private val PADDING_MOVIE_HORIZONTAL_LIST = 16.dp
private val PADDING_SCREEN = 16.dp

@ExperimentalAnimationApi
@Composable
fun MoviesScreen(mainViewModel: MainViewModel) {
    val popularMovies: List<Movie> by mainViewModel.popularMovies.observeAsState(emptyList())
    val upcomingMovies: List<Movie> by mainViewModel.upcomingMovies.observeAsState(emptyList())
    val allGenres: Set<Genre> by mainViewModel.allGenres.observeAsState(emptySet())

    Scaffold(topBar = { MoviesScreenTopBar() }) {
        MovieScreenContent(popularMovies, upcomingMovies, allGenres)
    }
}

@Composable
private fun MovieScreenContent(
    popularMovies: List<Movie>,
    upcomingMovies: List<Movie>,
    allGenres: Set<Genre>
) {
    Column(
        modifier = Modifier.verticalScroll(
            state = rememberScrollState()
        )
    ) {
        HeaderMovieSection(
            paddingTop = PADDING_SCREEN,
            title = stringResource(id = R.string.popular_movies)
        )
        MovieHorizontalList(
            modifier = Modifier.padding(top = PADDING_MOVIE_HORIZONTAL_LIST),
            movies = popularMovies,
            item = { position, currentMovie ->
                MoviePoster(
                    title = currentMovie.title,
                    path = currentMovie.posterPath,
                    voteAverage = currentMovie.voteAverage,
                    paddingValues = getHorizontalListPaddingValues(position, popularMovies.size)
                )
            }
        )
        HeaderMovieSection(title = stringResource(id = R.string.upcoming_movies))
        MovieHorizontalList(
            modifier = Modifier.padding(top = PADDING_MOVIE_HORIZONTAL_LIST),
            movies = upcomingMovies,
            item = { position, currentMovie ->
                MoviePoster(
                    title = currentMovie.title,
                    path = currentMovie.posterPath,
                    genre = allGenres.filter { genre -> currentMovie.genre_ids.any { it == genre.id } }
                        .joinToString { it.name },
                    paddingValues = getHorizontalListPaddingValues(
                        position,
                        upcomingMovies.size
                    )
                )
            }
        )
    }
}

@Composable
private fun HeaderMovieSection(paddingTop: Dp = 24.dp, title: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TitleMovieSection(
            modifier = Modifier
                .padding(top = paddingTop, start = PADDING_SCREEN)
                .weight(1f),
            text = title.uppercase()
        )
        ShowAllText()
    }
}

@Composable
private fun RowScope.ShowAllText() {
    ProvideTextStyle(value = MaterialTheme.typography.caption) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(top = PADDING_MOVIE_HORIZONTAL_LIST, end = PADDING_MOVIE_HORIZONTAL_LIST),
            text = stringResource(id = R.string.show_all),
            textAlign = TextAlign.End,
            color = Green
        )
    }
}

@Composable
private fun getHorizontalListPaddingValues(
    position: Int,
    listSize: Int
) = when (position) {
    0 -> PaddingValues(start = PADDING_SCREEN, end = 8.dp)
    listSize - 1 -> PaddingValues(end = PADDING_SCREEN)
    else -> PaddingValues(end = 8.dp)
}

@Composable
private fun TitleMovieSection(modifier: Modifier, text: String) {
    ProvideTextStyle(value = MaterialTheme.typography.subtitle2) {
        Text(
            modifier = modifier,
            text = text
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