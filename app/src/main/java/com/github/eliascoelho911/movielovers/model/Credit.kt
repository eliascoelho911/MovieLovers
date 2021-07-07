package com.github.eliascoelho911.movielovers.model

import com.google.gson.annotations.SerializedName

data class Credit(
    @SerializedName("adult") val adult: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("id") val id: Long,
    @SerializedName("known_for_department") val department: Department,
    @SerializedName("name") val name: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("profile_path") val profileImagePath: String?,
    @SerializedName("cast_id") val castId: Long,
    @SerializedName("character") val character: String,
    @SerializedName("credit_id") val creditId: String,
    @SerializedName("order") val order: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Credit

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

enum class Department(name: String) {
    @SerializedName("Directing")
    DIRECTING("Directing"),

    @SerializedName("Acting")
    ACTING("Acting"),

    @SerializedName("Crew")
    CREW("Crew"),

    @SerializedName("Costume & Make-Up")
    COSTUME_AND_MAKEUP("Costume & Make-Up"),

    @SerializedName("Production")
    PRODUCTION("Production"),

    @SerializedName("Art")
    ART("Art"),

    @SerializedName("Sound")
    SOUND("Sound"),

    @SerializedName("Writing")
    WRITING("Writing"),

    @SerializedName("Camera")
    CAMERA("Camera"),

    @SerializedName("Editing")
    EDITING("Editing"),

    @SerializedName("Visual Effects")
    VISUAL_EFFECTS("Visual Effects"),

    @SerializedName("Lighting")
    LIGHTING("Lighting");
}
