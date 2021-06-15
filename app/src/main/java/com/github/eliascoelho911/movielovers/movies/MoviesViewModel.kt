package com.github.eliascoelho911.movielovers.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.movielovers.repository.TheMoviesDBRepository
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val theMoviesDBRepository: TheMoviesDBRepository) :
    ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    init {
        viewModelScope.launch {
            _popularMovies.value = theMoviesDBRepository.getPopularMovies()
        }
    }
}