package com.github.eliascoelho911.movielovers.listmovies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.github.eliascoelho911.movielovers.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListMoviesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val movies: List<Movie> = savedStateHandle.get<ArrayList<Movie>>(KEY_ARG_MOVIES)!!.toList()
}