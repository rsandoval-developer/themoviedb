package com.rappi.movies.data.api.request

class GetMoviesRequestParams(
    val idMovies: String,
    val apiKey: String,
    val page: Int,
    val isPagination: Boolean = false
)