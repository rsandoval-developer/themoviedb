package com.rappi.movies.domain.datasource

import com.rappi.movies.domain.model.Movie
import io.reactivex.Observable

interface MoviesDatabaseDataSource {

    fun insertMovies(movies: List<Movie>, idMovies: String)

    fun deleteMovies()

    fun getMovies(idMovies: String): Observable<List<Movie>>
}
