package com.github.eliascoelho911.movielovers.repository

import com.github.eliascoelho911.movielovers.retrofit.TheMovieDBService
import com.github.eliascoelho911.movielovers.retrofit.cache.MovieCache
import com.github.eliascoelho911.movielovers.retrofit.cache.CacheType.POPULAR_MOVIES
import com.github.eliascoelho911.movielovers.retrofit.data.Movie
import javax.inject.Inject

class TheMoviesDBRepository @Inject constructor(
    private val theMovieDBService: TheMovieDBService,
    private val movieCache: MovieCache
) {
    suspend fun getPopularMovies(): List<Movie> {
        val cached: List<Movie>? = movieCache.getByType(POPULAR_MOVIES)
        if (cached != null)
            return cached

        val freshPopularMovies: List<Movie> = theMovieDBService.getPopularMovies().results
        movieCache.putToType(POPULAR_MOVIES, freshPopularMovies)
        return freshPopularMovies
    }
}