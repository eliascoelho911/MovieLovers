package com.github.eliascoelho911.movielovers.di

import com.github.eliascoelho911.movielovers.repository.cache.GenreCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class GenreCacheModule {
    @Provides
    fun provideGenreCache(): GenreCache = GenreCache()
}