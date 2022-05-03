package com.rick.appselect.ui

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rick.appselect.R
import com.rick.appselect.domain.model.Result
import com.rick.appselect.domain.repository.IMovieCatalogRepository
import com.rick.appselect.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSelectViewModel @Inject constructor(
    private val repository: IMovieCatalogRepository
) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val movieList = MutableLiveData<List<Result>>()
    val hasMore = MutableLiveData<Boolean>()
    val isLoading = MutableLiveData<Boolean>()
    val isRefresing = MutableLiveData<Boolean>()

    init {
        fetchMovieCatalog()
    }

    private fun fetchMovieCatalog() {
        viewModelScope.launch {
            repository.getMovieCalalog(PAGINATION, QUERY_ORDER)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            errorMessage.value = result.message ?: Resources.getSystem().getString(R.string.error_message)
                            isLoading.value = false
                        }
                        is Resource.Loading -> {
                            isLoading.value = result.isLoading
                        }
                        is Resource.Success -> {
                            movieList.postValue(
                                result.data!!.results
                            )
                            hasMore.value = result.data.hasMore
                            isLoading.value = false
                        }
                    }
                }
        }
    }

}

private const val QUERY_ORDER = "by-publication-date"
private const val PAGINATION = 20