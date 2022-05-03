package com.rick.appselect.domain.model

data class MovieCatalog(
    val results: List<Result>,
    val hasMore: Boolean
)
