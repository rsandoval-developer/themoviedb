package com.rappi.movies.domain.datasource

import com.rappi.movies.domain.model.Movie
import io.reactivex.Observable

interface MoviesApiDataSource {

    fun getMovies(
        movieId: String,
        apiKey: String,
        page: Int
    ): Observable<List<Movie>>

    fun searchMovies(
        apiKey: String,
        query: String
    ): Observable<List<Movie>>

}