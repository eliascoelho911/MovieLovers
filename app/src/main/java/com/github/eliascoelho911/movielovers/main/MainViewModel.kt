package com.github.eliascoelho911.movielovers.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.movielovers.repository.TMDBGenresRepository
import com.github.eliascoelho911.movielovers.repository.TMDBMoviesRepository
import com.github.eliascoelho911.movielovers.retrofit.data.Genre
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import com.github.eliascoelho911.movielovers.retrofit.data.TMDBGenresResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val tmdbMoviesRepository: TMDBMoviesRepository,
    private val tmdbGenresRepository: TMDBGenresRepository
) :
    ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    private val _upcomingMovies = MutableLiveData<List<Movie>>()
    val upcomingMovies: LiveData<List<Movie>> = _upcomingMovies

    private val _allGenres = MutableLiveData<Set<Genre>>()
    val allGenres: LiveData<Set<Genre>> = _allGenres

    init {
        viewModelScope.launch {
            _popularMovies.value =
                tmdbMoviesRepository.getPopularMovies().sortedByDescending { it.voteAverage }
            _upcomingMovies.value = tmdbMoviesRepository.getUpcomingMovies()
            _allGenres.value = tmdbGenresRepository.getGenres()
        }
    }
}