package com.github.eliascoelho911.movielovers.di

import com.github.eliascoelho911.movielovers.tmdb.cache.CreditsCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class CreditsCacheModule {
    @Provides
    fun provideCreditsCache(): CreditsCache = CreditsCache()
}