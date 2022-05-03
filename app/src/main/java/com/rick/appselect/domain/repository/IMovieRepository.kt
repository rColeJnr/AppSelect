package com.rick.appselect.domain.repository

import com.bumptech.glide.load.engine.Resource
import com.rick.appselect.domain.model.MovieCatalog
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    suspend fun getMovieCalalog(
        query: String
    ): Flow<Resource<List<MovieCatalog>>>

}