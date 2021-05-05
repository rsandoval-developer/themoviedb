package com.rappi.movies.data.datasourceimplementations

import com.rappi.movies.data.remote.exceptions.ApiResponseHandler
import com.rappi.movies.data.remote.services.MoviesServices
import com.rappi.movies.domain.datasource.MoviesApiDataSource
import com.rappi.movies.domain.mappers.MoviesMapper
import com.rappi.movies.domain.model.Movie
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import javax.inject.Inject

@ActivityRetainedScoped
class MoviesApiDataSourceImp @Inject constructor(
    private val moviesServices: MoviesServices,
    private val moviesMapper: MoviesMapper,
    private val apiResponseHandler: ApiResponseHandler
) : MoviesApiDataSource {

    override fun getMovies(moviesId: String, apiKey: String, page: Int): Observable<List<Movie>> =
        this.moviesServices.getMovies(moviesId, apiKey, page)
            .flatMap { response ->
                this.apiResponseHandler.handle(response)
            }.flatMap { movieResponse ->
                Observable.fromArray(movieResponse.results)
                    .flatMapIterable { it }
                    .map(moviesMapper::mapFromApi)
            }
            .toList()
            .toObservable()

    override fun searchMovies(apiKey: String, query: String): Observable<List<Movie>> =
        this.moviesServices.searchMovies(apiKey, query)
            .flatMap { response ->
                this.apiResponseHandler.handle(response)
            }.flatMap { movieResponse ->
                Observable.fromArray(movieResponse.results)
                    .flatMapIterable { it }
                    .map(moviesMapper::mapFromApi)
            }
            .toList()
            .toObservable()

}