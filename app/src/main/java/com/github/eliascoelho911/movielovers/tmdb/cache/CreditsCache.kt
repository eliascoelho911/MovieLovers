package com.github.eliascoelho911.movielovers.tmdb.cache

import com.github.eliascoelho911.movielovers.model.Credit


class CreditsCache {
    private val cached: MutableMap<Long, Set<Credit>> = mutableMapOf()

    fun get(movieId: Long): Set<Credit>? = cached[movieId]

    fun put(credits: Set<Credit>, movieId: Long) =
        cached.put(movieId, credits.toSet())
}
