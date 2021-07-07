package com.github.eliascoelho911.movielovers.tmdb

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.eliascoelho911.movielovers.model.Genre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val ERROR_GENRE_NOT_FOUND = "No genre was found"
private const val ERROR_GENRE_IDS_INVALID = "It genreIds is not valid"

class GenresFinder @Inject constructor(private val tmdbGenresRepository: TmdbGenresRepository) {
    fun findGenres(
        genreIds: List<Long>,
        coroutineScope: CoroutineScope
    ): LiveData<Result<List<Genre>>> {
        val genresLiveData: MutableLiveData<Result<List<Genre>>> = MutableLiveData()

        if (genreIds.isNotEmpty()) {
            coroutineScope.launch {
                val genres = tmdbGenresRepository.getGenres(genreIds)

                genresLiveData.value = if (genres.isNotEmpty())
                    Result.success(genres)
                else
                    Result.failure(IllegalArgumentException(ERROR_GENRE_NOT_FOUND))
            }
        } else {
            genresLiveData.value = Result.failure(IllegalArgumentException(ERROR_GENRE_IDS_INVALID))
        }

        return genresLiveData
    }
}