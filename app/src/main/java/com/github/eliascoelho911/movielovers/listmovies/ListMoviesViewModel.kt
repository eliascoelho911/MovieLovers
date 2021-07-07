package com.github.eliascoelho911.movielovers.listmovies

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.movielovers.model.Genre
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.tmdb.GenresFinder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListMoviesViewModel @Inject constructor(
    private val genresFinder: GenresFinder,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val movies: List<Movie> = savedStateHandle.get<ArrayList<Movie>>(KEY_ARG_MOVIES)!!.toList()

    fun findGenres(genreIds: List<Long>): LiveData<Result<List<Genre>>> =
        genresFinder.findGenres(genreIds, viewModelScope)
}