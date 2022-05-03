package com.rick.appselect.ui

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rick.appselect.R
import com.rick.appselect.domain.repository.IMovieCatalogRepository
import com.rick.appselect.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppSelectViewModel @Inject constructor(
    private val repository: IMovieCatalogRepository
) : ViewModel() {

    var state = MutableLiveData<AppSelectState>()

    init {
        fetchMovieCatalog()
    }

    private fun fetchMovieCatalog() {
        viewModelScope.launch {
            repository.getMovieCalalog(PAGINATION, QUERY_ORDER)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            state.value = state.value?.copy(
                                error = result.message ?: Resources.getSystem().getString(R.string.error_message)
                            )
                        }
                        is Resource.Loading -> {
                            state.value = state.value?.copy(
                                loading = result.isLoading
                            )
                        }
                        is Resource.Success -> {
                            state.value = state.value?.copy(
                                movieCatalog = result.data!!
                            )
                        }
                    }
                }
        }
    }

}

private const val QUERY_ORDER = "by-publication-date"
private const val PAGINATION = 20