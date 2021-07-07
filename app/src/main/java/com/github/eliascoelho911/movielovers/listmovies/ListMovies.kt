package com.github.eliascoelho911.movielovers.listmovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.base.MovieLoversLogo
import com.github.eliascoelho911.movielovers.base.movielist.MovieVerticalList
import com.github.eliascoelho911.movielovers.base.movielist.MovieVerticalListItem
import com.github.eliascoelho911.movielovers.base.movielist.listVerticalItemPadding
import com.github.eliascoelho911.movielovers.model.Movie
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

private val MovieVerticalListPadding = 16.dp
private val MovieItemPadding = 8.dp
private val ScreenPadding = 16.dp

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun ListMoviesScreen(
    listMoviesViewModel: ListMoviesViewModel,
    onBackPressed: () -> Unit,
    onClickMovieItem: (Movie) -> Unit,
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = { ListMoviesTopBar(onBackPressed = onBackPressed) }
    ) {
        ListMovieContent(
            listMoviesViewModel = listMoviesViewModel,
            onClickMovieItem = onClickMovieItem
        )
    }
}

@Composable
private fun ListMoviesTopBar(onBackPressed: () -> Unit) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        },
        title = { MovieLoversLogo() },
    )
}

@Composable
private fun ListMovieContent(
    listMoviesViewModel: ListMoviesViewModel,
    onClickMovieItem: (Movie) -> Unit
) {
    val movies = listMoviesViewModel.movies
    Column {
        MovieVerticalList(
            movies = movies,
            item = { position, currentMovie ->
                var namesOfGenres: String by remember { mutableStateOf("") }
                if (currentMovie.genreIds != null) {
                    val resultGenres by listMoviesViewModel.findGenres(genreIds = currentMovie.genreIds)
                        .observeAsState()
                    resultGenres?.onSuccess { freshGenres ->
                        namesOfGenres = freshGenres.joinToString { it.name }
                    }
                }
                val boxModifier = Modifier.navigationBarsPadding()
                    .takeIf { position == movies.size - 1 }
                    ?: Modifier
                Box(
                    modifier = boxModifier
                        .padding(horizontal = ScreenPadding)
                        .listVerticalItemPadding(
                            listSize = movies.size,
                            position = position,
                            paddingBetweenItems = MovieItemPadding,
                            paddingSides = MovieVerticalListPadding
                        )
                ) {
                    MovieVerticalListItem(
                        modifier = Modifier
                            .clickable(onClick = { onClickMovieItem(currentMovie) }),
                        pathImage = currentMovie.posterPath ?: "",
                        title = currentMovie.title,
                        genre = namesOfGenres,
                        releaseYear = currentMovie.releaseDate?.year,
                        voteAverage = currentMovie.voteAverage
                    )
                }
            })
    }
}