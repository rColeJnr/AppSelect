package com.rick.appselect.data.mappers

import com.rick.appselect.data.remote.LinkDto
import com.rick.appselect.data.remote.MovieCatalogDto
import com.rick.appselect.data.remote.MultimediaDto
import com.rick.appselect.data.remote.ResultDto
import com.rick.appselect.domain.model.Link
import com.rick.appselect.domain.model.MovieCatalog
import com.rick.appselect.domain.model.Multimedia
import com.rick.appselect.domain.model.Result

fun LinkDto.toLink(): Link =
    Link(
        url = url
    )

fun ResultDto.toResult(): Result =
    Result(
        title = display_title,
        summary = summary_short,
        link = link.toLink(),
        multimedia = multimedia.toMultimedia()
    )

fun MovieCatalogDto.toMovieCatalog(): MovieCatalog =
    MovieCatalog(
        results = results.map { it.toResult() },
        hasMore = has_more
    )

fun MultimediaDto.toMultimedia(): Multimedia =
    Multimedia(
        src = src
    )