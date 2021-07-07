package com.github.eliascoelho911.movielovers.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.movielovers.model.Genre
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.tmdb.GenresFinder
import com.github.eliascoelho911.movielovers.tmdb.TmdbMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tmdbMoviesRepository: TmdbMoviesRepository,
    private val genresFinder: GenresFinder
) : ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    init {
        viewModelScope.launch {
            _popularMovies.value =
                tmdbMoviesRepository.getPopularMovies().sortedByDescending { it.voteAverage }
            _upcomingMovies.value = tmdbMoviesRepository.getUpcomingMovies()
        }
    }

    fun findGenres(genreIds: List<Long>): LiveData<Result<List<Genre>>> =
        genresFinder.findGenres(genreIds, viewModelScope)
}