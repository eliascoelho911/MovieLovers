package com.github.eliascoelho911.movielovers.tmdb.cache

import com.github.eliascoelho911.movielovers.model.Movie


class MovieCache {
    private val cached: MutableSet<Movie> = mutableSetOf()
    private val cachedByType: MutableSet<CachedByTypeItem> = mutableSetOf()

    fun get(id: Long): Movie? =
        cached.firstOrNull { it.id == id }

    fun get(type: CacheType): List<Movie> {
        val ids: Set<Long> = cachedByType.filter { it.type == type }.map { it.id }.toSet()
        return cached.filter { ids.contains(it.id) }
    }

    fun put(type: CacheType? = null, movies: List<Movie>) {
        cached.addAll(movies)
        if (type != null)
            cachedByType.addAll(movies.map { CachedByTypeItem(id = it.id, type = type) })
    }
}

data class CachedByTypeItem(val id: Long, val type: CacheType)

enum class CacheType {
    POPULAR_MOVIES, UPCOMING_MOVIES
}