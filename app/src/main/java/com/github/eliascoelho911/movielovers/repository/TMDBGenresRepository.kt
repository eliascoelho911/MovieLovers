package com.github.eliascoelho911.movielovers.repository

import com.github.eliascoelho911.movielovers.repository.cache.GenreCache
import com.github.eliascoelho911.movielovers.retrofit.TMDBService
import com.github.eliascoelho911.movielovers.retrofit.data.Genre
import javax.inject.Inject

class TMDBGenresRepository @Inject constructor(
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
}