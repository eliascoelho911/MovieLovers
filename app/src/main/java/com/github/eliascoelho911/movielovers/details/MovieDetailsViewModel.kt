package com.github.eliascoelho911.movielovers.details

import androidx.lifecycle.*
import com.github.eliascoelho911.movielovers.model.Credit
import com.github.eliascoelho911.movielovers.model.Genre
import com.github.eliascoelho911.movielovers.model.Movie
import com.github.eliascoelho911.movielovers.tmdb.GenresFinder
import com.github.eliascoelho911.movielovers.tmdb.TmdbMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val genresFinder: GenresFinder,
    private val tmdbMoviesRepository: TmdbMoviesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val movie: Movie = savedStateHandle.get<Movie>(KEY_ARG_MOVIE)!!
    private val _credits: MutableLiveData<Set<Credit>> = MutableLiveData()
    val credits: LiveData<Set<Credit>> = _credits

    init {
        viewModelScope.launch {
            _credits.value = tmdbMoviesRepository.getCredits(movie.id)
        }
    }

    fun findGenres(genreIds: List<Long>): LiveData<Result<List<Genre>>> =
        genresFinder.findGenres(genreIds, viewModelScope)
}