package com.github.eliascoelho911.movielovers.tmdb

import com.github.eliascoelho911.movielovers.tmdb.cache.GenreCache
import com.github.eliascoelho911.movielovers.retrofit.TMDBService
import com.github.eliascoelho911.movielovers.model.Genre
import javax.inject.Inject

class TmdbGenresRepository @Inject constructor(
    private val genreCache: GenreCache,
    private val tmdbService: TMDBService
) {
    suspend fun getGenres(): Set<Genre> {
        val cached = genreCache.get()
        if (cached.isNotEmpty())
            return cached

        val freshGenres: Set<Genre> = tmdbService.getGenresList().genres
        genreCache.put(freshGenres)
        return freshGenres
    }

    suspend fun getGenres(genreIds: List<Long>): List<Genre> =
        getGenres().filter { genre -> genreIds.any { it == genre.id } }
}