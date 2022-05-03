package com.rick.appselect.data.repository

import com.rick.appselect.data.MovieCatalogApi
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

    override suspend fun getMovieCalalog(query: String): Flow<Resource<MovieCatalog>> {
        return flow {
            emit(Resource.Loading(true))
            val movieCatalog = try {
                val response = api.fetchMovieCatalog(20, QUERY_ORDER)
                emit(Resource.Success(
                    data = response.toMovieCatalog()
                ))
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(e.message ?: ""))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(e.message ?: ""))
                null
            }
        }
    }
}
private const val QUERY_ORDER = "by-publication-date"