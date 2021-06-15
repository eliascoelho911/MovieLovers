package com.github.eliascoelho911.movielovers.di

import com.github.eliascoelho911.movielovers.retrofit.cache.MovieCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class MovieCacheModule {
    @Provides
    fun providesMovieCache(): MovieCache = MovieCache()
}