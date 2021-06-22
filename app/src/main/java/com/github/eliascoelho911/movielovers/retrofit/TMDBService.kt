package com.github.eliascoelho911.movielovers.retrofit

import com.github.eliascoelho911.movielovers.retrofit.response.TMDBGenresResponse
import com.github.eliascoelho911.movielovers.retrofit.response.TMDBResponse
import retrofit2.http.GET

interface TMDBService {
    @GET("movie/popular")
    suspend fun getPopularMovies() : TMDBResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies() : TMDBResponse

    @GET("genre/movie/list")
    suspend fun getGenresList() : TMDBGenresResponse
}