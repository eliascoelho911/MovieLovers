package com.github.eliascoelho911.movielovers.tmdb.cache

import com.github.eliascoelho911.movielovers.model.Movie


class MovieCache {
    private val cached: MutableSet<Movie> = mutableSetOf()
    private val cachedByType: MutableMap<CacheType, Set<Long>> = mutableMapOf()

    fun get(id: Long): Movie? =
        cached.firstOrNull { it.id == id }

    fun get(type: CacheType): List<Movie> {
        val ids: Set<Long>? = cachedByType[type]
        return cached.filter { ids?.contains(it.id) == true }
    }

    fun put(type: CacheType? = null, movies: List<Movie>) {
        cached.addAll(movies)
        if (type != null)
            cachedByType[type] = movies.map { it.id }.toSet()
    }
}

enum class CacheType {
    POPULAR_MOVIES, UPCOMING_MOVIES
}