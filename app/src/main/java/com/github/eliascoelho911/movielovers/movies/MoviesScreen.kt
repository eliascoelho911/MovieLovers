package com.github.eliascoelho911.movielovers.movies

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.eliascoelho911.movielovers.components.MovieLoversLogo
import com.github.eliascoelho911.movielovers.R
import com.github.eliascoelho911.movielovers.components.CustomTextField
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import com.github.eliascoelho911.movielovers.retrofit.data.PopularMovies
import com.github.eliascoelho911.movielovers.ui.theme.DarkGray
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@ExperimentalPagerApi
@ExperimentalAnimationApi
@Composable
fun MoviesScreen(moviesViewModel: MoviesViewModel = viewModel()) {
    val popularMovies: State<List<Movie>?> = moviesViewModel.popularMovies.observeAsState()
    Scaffold(topBar = { MoviesScreenTopBar() }) {
        val pagerState = rememberPagerState(pageCount = 5)
        HorizontalPager(state = pagerState) { page ->
            Text(
                text = "Title: ${popularMovies.value?.get(page)?.title}",
                modifier = Modifier.fillMaxWidth()
            )
        }
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
        })
}