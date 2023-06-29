package com.boradincer.moviesapp.di

import com.boradincer.moviesapp.data.network.MoviesAppRepository
import com.boradincer.moviesapp.data.network.MoviesAppRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoviesAppModule {
    @Provides
    @Singleton
    fun provideNotesRepository(): MoviesAppRepository = MoviesAppRepositoryImpl()
}