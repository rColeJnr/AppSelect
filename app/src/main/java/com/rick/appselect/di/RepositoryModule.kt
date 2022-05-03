package com.rick.appselect.di

import com.rick.appselect.data.MovieCatalogRepositoryImpl
import com.rick.appselect.domain.repository.IMovieCatalogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieCatalogRepository(
        movieCatalogRepositoryImpl: MovieCatalogRepositoryImpl
    ): IMovieCatalogRepository

}