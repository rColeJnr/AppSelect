package com.rick.appselect.di

import com.rick.appselect.data.MovieCatalogApi
import com.rick.appselect.data.MovieCatalogApi.Companion.BASE_URL
import com.rick.appselect.data.repository.MovieCatalogRepositoryImpl
import com.rick.appselect.domain.repository.IMovieCatalogRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesMovieCatalogApi(): MovieCatalogApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }).build()
            )
            .build()
            .create()
    }
}