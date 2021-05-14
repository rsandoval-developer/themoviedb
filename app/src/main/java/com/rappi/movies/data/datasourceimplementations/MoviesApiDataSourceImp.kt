package com.rappi.movies.data.datasourceimplementations

import com.rappi.movies.data.api.exceptions.ApiResponseHandler
import com.rappi.movies.data.api.services.MoviesServices
import com.rappi.movies.domain.datasource.MoviesApiDataSource
import com.rappi.movies.domain.mappers.MoviesMapper
import com.rappi.movies.domain.mappers.VideosMapper
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.model.Video
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import javax.inject.Inject

@ActivityRetainedScoped
class MoviesApiDataSourceImp @Inject constructor(
    private val moviesServices: MoviesServices,
    private val moviesMapper: MoviesMapper,
    private val videosMapper: VideosMapper,
    private val apiResponseHandler: ApiResponseHandler
) : MoviesApiDataSource {

    override fun getMovies(moviesId: String, apiKey: String, page: Int): Observable<List<Movie>> =
        moviesServices.getMovies(moviesId, apiKey, page)
            .flatMap { response ->
                apiResponseHandler.handle(response)
            }
            .map(moviesMapper::mapFromApi)

    override fun searchMovies(apiKey: String, query: String): Observable<List<Movie>> =
        moviesServices.searchMovies(apiKey, query)
            .flatMap { response ->
                apiResponseHandler.handle(response)
            }
            .map(moviesMapper::mapFromApi)

    override fun getVideos(movieId: Int, apiKey: String): Observable<List<Video>> =
        moviesServices.getVideos(movieId, apiKey)
            .flatMap { response ->
                apiResponseHandler.handle(response)
            }
            .map(videosMapper::mapFromApi)

}