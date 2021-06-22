package com.github.eliascoelho911.movielovers.tmdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.eliascoelho911.movielovers.model.Genre
import com.github.eliascoelho911.movielovers.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException
import javax.inject.Inject

@HiltViewModel
class TmdbViewModel @Inject constructor(
    private val tmdbMoviesRepository: TmdbMoviesRepository,
    private val tmdbGenresRepository: TmdbGenresRepository
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

    fun findGenres(genreIds: List<Long>, onFinish: (Result<List<Genre>>) -> Unit) {
        viewModelScope.launch {
            if (genreIds.isNotEmpty()) {
                val genres = tmdbGenresRepository.getGenres(genreIds)

                if (genres.isNotEmpty())
                    onFinish(Result.success(genres))
                else
                    onFinish(Result.failure(IllegalArgumentException("Nenhum genero foi encontrado")))
            }
        }
    }
}