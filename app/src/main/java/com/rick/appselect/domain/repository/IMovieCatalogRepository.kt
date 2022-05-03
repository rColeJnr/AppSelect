package com.rick.appselect.domain.repository

import com.rick.appselect.domain.model.MovieCatalog
import com.rick.appselect.util.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieCatalogRepository {

    suspend fun getMovieCalalog(
        query: String
    ): Flow<Resource<List<MovieCatalog>>>

}