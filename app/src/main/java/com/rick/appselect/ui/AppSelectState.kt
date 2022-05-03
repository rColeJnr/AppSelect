package com.rick.appselect.ui

import com.rick.appselect.domain.model.MovieCatalog

data class AppSelectState(
    val error: String,
    val loading: Boolean = true,
    val movieCatalog: MovieCatalog,
    val isRefreshing: Boolean = false
)
