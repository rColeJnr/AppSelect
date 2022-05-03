package com.rick.appselect.ui

import android.content.res.Resources
import androidx.lifecycle.LiveData
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

    private val _movieList = MutableLiveData<List<Result>>()
    val movieList: LiveData<List<Result>> = _movieList

    private val _hasMore = MutableLiveData<Boolean>()
    val hasMore: LiveData<Boolean> = _hasMore

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> = _isRefreshing

    private var paginationNumber = 15

    init {
        fetchMovieCatalog(paginationNumber)
    }

    private fun fetchMovieCatalog(pages: Int) {
        viewModelScope.launch {
            repository.getMovieCalalog(pages)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            errorMessage.value = result.message ?: Resources.getSystem().getString(R.string.error_message)
                            _isRefreshing.value = false
                        }
                        is Resource.Loading -> {
                            _isLoading.value = result.isLoading
                            if (isRefreshing.value == true) _isLoading.value = false
                        }
                        is Resource.Success -> {
                            _movieList.postValue(
                                result.data!!.results
                            )
                            _hasMore.value = result.data.hasMore
                            _isRefreshing.value = false
                        }
                    }
                }
        }
    }

    fun loadMoreData(){
        _isLoading.postValue(true)
        paginationNumber += 10
        fetchMovieCatalog(paginationNumber)
    }

    fun refreshData(){
        _isRefreshing.postValue(true)
        fetchMovieCatalog(paginationNumber)
    }
}