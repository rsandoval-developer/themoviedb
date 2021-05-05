package com.rappi.movies.domain.usecases

import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.repository.MoviesRepository
import com.rappi.movies.domain.usecases.base.ParamsUseCase
import io.reactivex.Observable
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) :
    ParamsUseCase<List<Movie>, ParamsSearchMovies>() {

    override fun createObservable(params: ParamsSearchMovies): Observable<List<Movie>> =
        this.moviesRepository.searchMovies(params.apiKey, params.query)
}

class ParamsSearchMovies(
    val apiKey: String,
    val query: String
)