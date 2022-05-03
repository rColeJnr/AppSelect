package com.rick.appselect.domain.repository

import com.rick.appselect.domain.model.MovieCatalog
import com.rick.appselect.util.Resource
import kotlinx.coroutines.flow.Flow

interface IMovieCatalogRepository {

    suspend fun getMovieCalalog(offset: Int, queryOrder: String): Flow<Resource<MovieCatalog>>

}