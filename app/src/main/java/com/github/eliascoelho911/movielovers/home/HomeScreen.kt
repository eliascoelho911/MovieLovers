package com.github.eliascoelho911.movielovers.home

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.base.CustomTextField
import com.github.eliascoelho911.movielovers.base.MovieHorizontalList
import com.github.eliascoelho911.movielovers.base.MovieLoversLogo
import com.github.eliascoelho911.movielovers.base.MovieHorizontalListItem
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.tmdb.TmdbViewModel
import com.github.eliascoelho911.movielovers.ui.theme.DarkGray
import com.github.eliascoelho911.movielovers.ui.theme.Green
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

private val MovieHorizontalListPadding = 16.dp
private val ScreenPadding = 16.dp

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    tmdbViewModel: TmdbViewModel,
    onClickShowAll: (List<Movie>) -> Unit
) {
    val popularMovies: List<Movie> by tmdbViewModel.popularMovies.observeAsState(emptyList())
    val upcomingMovies: List<Movie> by tmdbViewModel.upcomingMovies.observeAsState(emptyList())
    var searchedText: String by remember { mutableStateOf("") }
    Scaffold(modifier = Modifier.statusBarsPadding(),
        topBar = {
            HomeScreenTopBar(
                searchedText = searchedText,
                onSearchValueChanged = { searchedText = it }
            )
        }) {
        HomeScreenContent(
            modifier = modifier,
            popularMovies = popularMovies,
            upcomingMovies = upcomingMovies,
            tmdbViewModel = tmdbViewModel,
            onClickShowAll = onClickShowAll
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun HomeScreenTopBar(searchedText: String, onSearchValueChanged: (String) -> Unit) {
    var logoIsVisible by remember { mutableStateOf(true) }
    TopAppBar(title = {
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
private fun HomeScreenContent(
    modifier: Modifier,
    popularMovies: List<Movie>,
    upcomingMovies: List<Movie>,
    tmdbViewModel: TmdbViewModel,
    onClickShowAll: (List<Movie>) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .navigationBarsPadding()
    ) {
        MovieSection(
            title = stringResource(id = R.string.popular_movies),
            movies = popularMovies,
            item = { _, movie ->
                MovieHorizontalListItem(
                    title = movie.title,
                    path = movie.posterPath ?: "",
                    voteAverage = movie.voteAverage
                )
            },
            onClickShowAll = onClickShowAll
        )
        MovieSection(
            title = stringResource(id = R.string.upcoming_movies),
            movies = upcomingMovies,
            item = { _, movie ->
                var namesOfGenres: String by remember { mutableStateOf("") }
                if (movie.genreIds != null)
                    tmdbViewModel.findGenres(genreIds = movie.genreIds, onFinish = { result ->
                        result.onSuccess { genres ->
                            namesOfGenres = genres.joinToString { it.name }
                        }
                    })
                MovieHorizontalListItem(
                    title = movie.title,
                    path = movie.posterPath ?: "",
                    genre = namesOfGenres
                )
            },
            onClickShowAll = onClickShowAll
        )
    }
}

@Composable
private fun MovieSection(
    onClickShowAll: (List<Movie>) -> Unit,
    title: String,
    movies: List<Movie>,
    item: @Composable (position: Int, movie: Movie) -> Unit
) {
    HeaderMovieSection(
        title = title,
        onClickShowAll = { onClickShowAll(movies) }
    )
    MovieHorizontalList(
        modifier = Modifier.padding(top = MovieHorizontalListPadding),
        movies = movies,
        item = { position, currentMovie ->
            item(position = position, movie = currentMovie)
        },
        horizontalPadding = ScreenPadding
    )
}

@Composable
private fun HeaderMovieSection(title: String, onClickShowAll: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        ProvideTextStyle(value = MaterialTheme.typography.subtitle2) {
            Text(
                modifier = Modifier
                    .padding(top = ScreenPadding, start = ScreenPadding)
                    .weight(1f),
                text = title.uppercase()
            )
        }
        ProvideTextStyle(value = MaterialTheme.typography.caption) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = MovieHorizontalListPadding, end = MovieHorizontalListPadding)
                    .clickable(onClick = onClickShowAll),
                text = stringResource(id = R.string.show_all),
                textAlign = TextAlign.End,
                color = Green
            )
        }
    }
}