package com.rappi.movies.data.remote.request

class GetMoviesRequestParams(
    val idMovies: String,
    val apiKey: String,
    val page: Int,
    val isPagination: Boolean = false
)