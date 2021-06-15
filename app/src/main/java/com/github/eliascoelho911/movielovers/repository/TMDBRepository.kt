package com.github.eliascoelho911.movielovers.repository

import com.github.eliascoelho911.movielovers.retrofit.TMDBService
import com.github.eliascoelho911.movielovers.retrofit.cache.MovieCache
import com.github.eliascoelho911.movielovers.retrofit.cache.CacheType.POPULAR_MOVIES
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import javax.inject.Inject

class TMDBRepository @Inject constructor(
    private val TMDBService: TMDBService,
    private val movieCache: MovieCache
) {
    suspend fun getPopularMovies(): List<Movie> {
        val cached: List<Movie>? = movieCache.getByType(POPULAR_MOVIES)
        if (cached != null)
            return cached

        val freshPopularMovies: List<Movie> = TMDBService.getPopularMovies().results
        movieCache.putToType(POPULAR_MOVIES, freshPopularMovies)
        return freshPopularMovies
    }
}