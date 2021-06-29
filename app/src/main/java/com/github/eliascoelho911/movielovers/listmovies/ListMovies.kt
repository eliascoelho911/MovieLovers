package com.github.eliascoelho911.movielovers.listmovies

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.eliascoelho911.movielovers.base.MovieLoversLogo
import com.github.eliascoelho911.movielovers.base.movielist.MovieVerticalList
import com.github.eliascoelho911.movielovers.base.movielist.MovieVerticalListItem
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.tmdb.TmdbViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.base.MovieLoversTopAppBar

private val MovieVerticalListPadding = 16.dp
private val ScreenPadding = 16.dp

@ExperimentalFoundationApi
@ExperimentalAnimationApi
@Composable
fun ListMoviesScreen(
    listMoviesViewModel: ListMoviesViewModel,
    tmdbViewModel: TmdbViewModel,
    onBackPressed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = { ListMoviesTopBar(onBackPressed = onBackPressed) }
    ) {
        ListMovieContent(
            listMoviesViewModel = listMoviesViewModel,
            tmdbViewModel = tmdbViewModel
        )
    }
}

@Composable
private fun ListMoviesTopBar(onBackPressed: () -> Unit) {
    MovieLoversTopAppBar(title = { MovieLoversLogo() },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = stringResource(id = R.string.back)
                )
            }
        })
}

@Composable
private fun ListMovieContent(
    listMoviesViewModel: ListMoviesViewModel,
    tmdbViewModel: TmdbViewModel
) {
    val movies = listMoviesViewModel.movies
    Column {
        MovieVerticalList(
            modifier = Modifier.padding(horizontal = ScreenPadding),
            movies = movies,
            item = { position, currentMovie ->
                val genres = remember { mutableStateOf("") }
                if (currentMovie.genreIds != null && currentMovie.genreIds.isNotEmpty())
                    tmdbViewModel.findGenres(
                        genreIds = currentMovie.genreIds,
                        onFinish = { result ->
                            result.onSuccess { genresReturned ->
                                genres.value = genresReturned.joinToString { it.name }
                            }
                        })
                MovieListItem(position, movies.size, currentMovie, genres)
            })
    }
}

@Composable
private fun MovieListItem(
    position: Int,
    listSize: Int,
    currentMovie: Movie,
    genres: MutableState<String>
) {
    val paddingValues = when (position) {
        0 -> PaddingValues(top = MovieVerticalListPadding, bottom = 8.dp)
        listSize - 1 -> PaddingValues(bottom = MovieVerticalListPadding)
        else -> PaddingValues(bottom = 8.dp)
    }
    val modifier = if (listSize - 1 == position)
        Modifier.navigationBarsPadding()
    else
        Modifier

    Box(modifier = modifier.padding(paddingValues = paddingValues)) {
        MovieVerticalListItem(
            pathImage = currentMovie.posterPath ?: "",
            title = currentMovie.title,
            genre = genres.value,
            releaseYear = 2020,
            voteAverage = currentMovie.voteAverage
        )
    }
}