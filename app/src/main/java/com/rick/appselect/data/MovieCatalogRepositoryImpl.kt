package com.rick.appselect.data

import com.rick.appselect.data.mappers.toMovieCatalog
import com.rick.appselect.domain.model.MovieCatalog
import com.rick.appselect.domain.repository.IMovieCatalogRepository
import com.rick.appselect.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieCatalogRepositoryImpl @Inject constructor(
    private val api: MovieCatalogApi
): IMovieCatalogRepository {

    // @Param queryOrder -  paginate through results, 20 at a time.
    override suspend fun getMovieCalalog(offset: Int): Flow<Resource<MovieCatalog>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val response = api.fetchMovieCatalog(offset, QUERY_ORDER)
                emit(Resource.Success(
                    data = response.toMovieCatalog()
                ))
                emit(Resource.Loading(false))
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(e.message ?: ""))
                emit(Resource.Loading(false))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: ""))
                emit(Resource.Loading(false))
            }
        }
    }
}

private const val QUERY_ORDER = "by-publication-date"