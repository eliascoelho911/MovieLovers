package com.github.eliascoelho911.movielovers.tmdb.cache

import com.github.eliascoelho911.movielovers.model.Genre

class GenreCache {
    private val cached: MutableSet<Genre> = mutableSetOf()

    fun get(): Set<Genre> = cached

    fun put(genres: Collection<Genre>) =
        cached.addAll(genres)
}
