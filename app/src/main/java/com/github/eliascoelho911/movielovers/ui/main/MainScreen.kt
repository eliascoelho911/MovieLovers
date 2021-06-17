package com.github.eliascoelho911.movielovers.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.retrofit.data.Genre
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import com.github.eliascoelho911.movielovers.ui.components.*
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
    var searchedText: String by remember { mutableStateOf("") }

    Scaffold(topBar = {
        MainScreenTopBar(
            searchedText = searchedText,
            onSearchValueChanged = { searchedText = it }
        )
    }) {
        MovieScreenContent(popularMovies, upcomingMovies, allGenres)
    }
}

@ExperimentalAnimationApi
@Composable
private fun MainScreenTopBar(searchedText: String, onSearchValueChanged: (String) -> Unit) {
    var logoIsVisible by remember { mutableStateOf(true) }
    MovieLoversTopBar(title = {
        Row(
            modifier = Modifier
                .fillMaxSize(),
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
                ) + fadeOut()
            ) {
                ProvideTextStyle(value = MaterialTheme.typography.body1) {
                    CustomTextField(
                        modifier = Modifier.fillMaxWidth(),
                        text = searchedText,
                        onValueChanged = onSearchValueChanged,
                        placeholder = {
                            ProvideTextStyle(value = MaterialTheme.typography.body1) {
                                Text(
                                    text = stringResource(id = R.string.enter_movie_name),
                                    color = Color.LightGray
                                )
                            }
                        }
                    )
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
    }, actions = {
        IconButton(onClick = { logoIsVisible = false }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(
                    id = R.string.search
                ),
                tint = DarkGray
            )
        }
    }, navigationIcon = {
        Box {
            AnimatedVisibility(
                visible = logoIsVisible,
                enter = slideInHorizontally()
                        + fadeIn(initialAlpha = 0.3f),
                exit = shrinkHorizontally(
                    shrinkTowards = Alignment.Start
                ) + fadeOut()
            ) {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_menu_burger),
                        contentDescription = stringResource(
                            id = R.string.menu
                        ),
                        tint = DarkGray
                    )
                }
            }
            AnimatedVisibility(
                visible = !logoIsVisible,
                enter = slideInHorizontally()
                        + fadeIn(initialAlpha = 0.3f),
                exit = shrinkHorizontally(
                    shrinkTowards = Alignment.Start
                ) + fadeOut()
            ) {
                IconButton(onClick = {
                    logoIsVisible = true
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = stringResource(
                            id = R.string.back
                        ),
                        tint = DarkGray
                    )
                }
            }
        }
    })
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