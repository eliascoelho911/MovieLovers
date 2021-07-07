package com.github.eliascoelho911.movielovers.retrofit.response

import com.github.eliascoelho911.movielovers.model.Credit

data class TMDBMovieCreditsResponse(val cast: Set<Credit>, val crew: Set<Credit>)
