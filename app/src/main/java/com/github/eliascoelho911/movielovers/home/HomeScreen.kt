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
import com.github.eliascoelho911.movielovers.base.MovieLoversLogo
import com.github.eliascoelho911.movielovers.base.MovieLoversTextField
import com.github.eliascoelho911.movielovers.base.movielist.MovieHorizontalList
import com.github.eliascoelho911.movielovers.base.movielist.MovieHorizontalListItem
import com.github.eliascoelho911.movielovers.base.movielist.listHorizontalItemPadding
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.ui.theme.DarkGray
import com.github.eliascoelho911.movielovers.ui.theme.Green
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

private val MovieItemPadding = 8.dp
private val ScreenPadding = 16.dp
private val MovieSectionItemPadding = 16.dp

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel,
    onClickShowAll: (List<Movie>) -> Unit,
    onClickMovieItem: (Movie) -> Unit
) {
    val popularMovies: List<Movie> by homeViewModel.popularMovies.observeAsState(emptyList())
    val upcomingMovies: List<Movie> by homeViewModel.upcomingMovies.observeAsState(emptyList())
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
            homeViewModel = homeViewModel,
            onClickShowAll = onClickShowAll,
            onClickMovieItem = onClickMovieItem
        )
    }
}

@ExperimentalAnimationApi
@Composable
private fun HomeScreenTopBar(searchedText: String, onSearchValueChanged: (String) -> Unit) {
    var logoIsVisible by remember { mutableStateOf(true) }
    TopAppBar(
        navigationIcon = {
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
        },
        actions = {
            IconButton(onClick = { logoIsVisible = false }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(
                        id = R.string.search
                    ),
                    tint = DarkGray
                )
            }
        },
        title = {
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
                MovieLoversTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = searchedText,
                    onValueChanged = onSearchValueChanged,
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.enter_movie_name),
                            color = Color.LightGray,
                            style = MaterialTheme.typography.body1
                        )
                    },
                    textStyle = MaterialTheme.typography.body1
                )
            }
            AnimatedVisibility(
                visible = logoIsVisible,
                enter = fadeIn(initialAlpha = 0.3f),
                exit = fadeOut()
            ) {
                MovieLoversLogo()
            }
        },
    )
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier,
    popularMovies: List<Movie>,
    upcomingMovies: List<Movie>,
    homeViewModel: HomeViewModel,
    onClickShowAll: (List<Movie>) -> Unit,
    onClickMovieItem: (Movie) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(state = rememberScrollState())
            .navigationBarsPadding()
    ) {
        MovieSection(
            title = stringResource(id = R.string.popular_movies),
            movies = popularMovies,
            item = { _, movie, modifier ->
                MovieHorizontalListItem(
                    modifier = modifier.clickable(onClick = { onClickMovieItem(movie) }),
                    title = movie.title,
                    pathImage = movie.posterPath ?: "",
                    voteAverage = movie.voteAverage
                )
            },
            onClickShowAll = onClickShowAll
        )
        MovieSection(
            title = stringResource(id = R.string.upcoming_movies),
            movies = upcomingMovies,
            item = { _, currentMovie, modifier ->
                var namesOfGenres: String by remember { mutableStateOf("") }
                if (currentMovie.genreIds != null) {
                    val resultGenres by homeViewModel.findGenres(genreIds = currentMovie.genreIds)
                        .observeAsState()
                    resultGenres?.onSuccess { freshGenres ->
                        namesOfGenres = freshGenres.joinToString { it.name }
                    }
                }
                MovieHorizontalListItem(
                    modifier = modifier.clickable(onClick = { onClickMovieItem(currentMovie) }),
                    title = currentMovie.title,
                    pathImage = currentMovie.posterPath ?: "",
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
    item: @Composable (position: Int, movie: Movie, modifier: Modifier) -> Unit
) {
    HeaderMovieSection(
        title = title,
        onClickShowAll = { onClickShowAll(movies) }
    )
    MovieHorizontalList(
        modifier = Modifier.padding(top = ScreenPadding - MovieItemPadding),
        movies = movies,
        item = { position, currentMovie ->
            item(
                position = position,
                movie = currentMovie,
                modifier = Modifier
                    .padding(vertical = MovieItemPadding)
                    .listHorizontalItemPadding(
                        listSize = movies.size,
                        position = position,
                        paddingBetweenItems = MovieItemPadding,
                        paddingSides = ScreenPadding
                    )
            )
        }
    )
}

@Composable
private fun HeaderMovieSection(title: String, onClickShowAll: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier
                .padding(top = ScreenPadding, start = ScreenPadding)
                .weight(1f),
            text = title.uppercase(),
            style = MaterialTheme.typography.subtitle2
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(top = MovieSectionItemPadding, end = MovieSectionItemPadding)
                .clickable(onClick = onClickShowAll),
            text = stringResource(id = R.string.show_all),
            textAlign = TextAlign.End,
            color = Green,
            style = MaterialTheme.typography.caption
        )
    }
}