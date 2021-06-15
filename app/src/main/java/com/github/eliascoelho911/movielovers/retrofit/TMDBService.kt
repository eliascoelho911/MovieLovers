package com.github.eliascoelho911.movielovers.retrofit

import com.github.eliascoelho911.movielovers.retrofit.data.PopularMovies
import retrofit2.http.GET

interface TMDBService {
    @GET("movie/popular")
    suspend fun getPopularMovies() : PopularMovies
}