package com.github.eliascoelho911.movielovers.retrofit

import com.github.eliascoelho911.movielovers.retrofit.response.TMDBMovieCreditsResponse
import com.github.eliascoelho911.movielovers.retrofit.response.TMDBMovieGenresResponse
import com.github.eliascoelho911.movielovers.retrofit.response.TMDBMovieListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface TMDBService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): TMDBMovieListResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): TMDBMovieListResponse

    @GET("genre/movie/list")
    suspend fun getGenresList(): TMDBMovieGenresResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCredits(@Path(value = "movie_id") movieId: Long): TMDBMovieCreditsResponse
}