package com.github.eliascoelho911.movielovers.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.movielovers.repository.TMDBRepository
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val TMDBRepository: TMDBRepository) :
    ViewModel() {
    private val _popularMovies = MutableLiveData<List<Movie>>()
    val popularMovies: LiveData<List<Movie>> = _popularMovies

    init {
        viewModelScope.launch {
            _popularMovies.value = TMDBRepository.getPopularMovies()
        }
    }
}