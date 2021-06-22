package com.github.eliascoelho911.movielovers.tmdb

private const val BASE_URL = "https://image.tmdb.org/t/p/"

const val w300: String = "w300"
const val w780: String = "w780"
const val w1280: String = "w1280"
const val original: String = "original"

object TMDBImage {
    fun getAbsoluteUrl(size: String, path: String) =
        "$BASE_URL$size/$path"
}