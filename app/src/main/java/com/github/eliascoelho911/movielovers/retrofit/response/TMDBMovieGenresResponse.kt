package com.github.eliascoelho911.movielovers.retrofit.response

import com.github.eliascoelho911.movielovers.model.Genre

data class TMDBMovieGenresResponse(val genres: Set<Genre>)