package com.rick.appselect.data

import com.rick.appselect.data.remote.MovieCatalogDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieCatalogApi {

    @GET("svc/movies/v2/reviews/all.json?")
    suspend fun fetchMovieCatalog(
        @Query("offset") offset: Int,
        @Query("order") order: String,
    ): MovieCatalogDto

    companion object {
        const val BASE_URL = "https://api.nytimes.com/"
    }

}