package com.github.eliascoelho911.movielovers.repository

import com.github.eliascoelho911.movielovers.retrofit.TMDBService
import com.github.eliascoelho911.movielovers.repository.cache.MovieCache
import com.github.eliascoelho911.movielovers.repository.cache.CacheType.POPULAR_MOVIES
import com.github.eliascoelho911.movielovers.repository.cache.CacheType.UPCOMING_MOVIES
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import javax.inject.Inject

class TMDBMoviesRepository @Inject constructor(
    private val tmdbService: TMDBService,
    private val movieCache: MovieCache
) {
    suspend fun getPopularMovies(): List<Movie> {
        val cached: List<Movie> = movieCache.get(type = POPULAR_MOVIES)
        if (cached.isNotEmpty())
            return cached

        val freshPopularMovies: List<Movie> = tmdbService.getPopularMovies().results
        movieCache.put(POPULAR_MOVIES, freshPopularMovies)
        return freshPopularMovies
    }

    suspend fun getUpcomingMovies(): List<Movie> {
        val cached: List<Movie> = movieCache.get(type = UPCOMING_MOVIES)
        if (cached.isNotEmpty())
            return cached

        val freshUpcomingMovies: List<Movie> = tmdbService.getUpcomingMovies().results
        movieCache.put(UPCOMING_MOVIES, freshUpcomingMovies)
        return freshUpcomingMovies
    }
}