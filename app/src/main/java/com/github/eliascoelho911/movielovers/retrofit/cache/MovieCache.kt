package com.github.eliascoelho911.movielovers.retrofit.cache

import com.github.eliascoelho911.movielovers.retrofit.data.Movie


class MovieCache {
    private val cached: MutableSet<Movie> = mutableSetOf()
    private val cachedToType: MutableMap<CacheType, List<Movie>> = mutableMapOf()

    fun get(id: Long): Movie? =
        cached.firstOrNull { it.id == id }

    fun getByType(type: CacheType): List<Movie>? =
        cachedToType[type]

    fun put(movie: Movie) =
        cached.add(movie)

    fun putToType(type: CacheType, movies: List<Movie>) =
        cachedToType.put(type, movies)
}

enum class CacheType {
    POPULAR_MOVIES
}