package com.github.eliascoelho911.movielovers.model

import com.google.gson.annotations.SerializedName

data class Genre(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Genre

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
