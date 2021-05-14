package com.rappi.movies.domain.usecases

import com.rappi.movies.data.api.request.GetMoviesRequestParams
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.repository.MoviesRepository
import com.rappi.movies.domain.usecases.base.ParamsUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) :
    ParamsUseCase<List<Movie>, GetMoviesRequestParams>() {

    override fun createObservable(params: GetMoviesRequestParams): Observable<List<Movie>> =
        moviesRepository.getMovies(params)
}