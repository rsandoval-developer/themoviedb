package com.rappi.movies.data.repositoryimplementations

import androidx.annotation.VisibleForTesting
import com.rappi.movies.data.remote.exceptions.AppException
import com.rappi.movies.data.remote.request.GetMoviesRequestParams
import com.rappi.movies.domain.datasource.MoviesApiDataSource
import com.rappi.movies.domain.datasource.MoviesDatabaseDataSource
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.model.Video
import com.rappi.movies.domain.repository.MoviesRepository
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import javax.inject.Inject

@ActivityRetainedScoped
class MoviesRepositoryImp @Inject constructor(
    private val moviesApiDataSource: MoviesApiDataSource,
    private val moviesDatabaseDataSource: MoviesDatabaseDataSource
) : MoviesRepository {

    @VisibleForTesting
    internal fun cacheMovies(movies: List<Movie>?, idMovies: String) {
        if (movies != null) {
            this.moviesDatabaseDataSource.insertMovies(movies, idMovies)
        }
    }

    override fun getMovies(
        getMoviesRequestParams: GetMoviesRequestParams
    ): Observable<List<Movie>> =
        this.moviesApiDataSource.getMovies(
            getMoviesRequestParams.idMovies,
            getMoviesRequestParams.apiKey,
            getMoviesRequestParams.page
        )
            .doOnNext { movies ->
                this.cacheMovies(
                    movies,
                    getMoviesRequestParams.idMovies,
                )
            }.onErrorResumeNext(
                if (!getMoviesRequestParams.isPagination) {
                    this.moviesDatabaseDataSource.getMovies(getMoviesRequestParams.idMovies)
                        .onErrorResumeNext(
                            Observable.error(AppException(AppException.Type.ERROR_NETWORK))
                        )
                } else {
                    Observable.error(AppException(AppException.Type.ERROR_NETWORK))
                }
            )

    override fun searchMovies(apiKey: String, query: String): Observable<List<Movie>> =
        this.moviesApiDataSource.searchMovies(apiKey, query)

    override fun getVideos(movieId: Int, apiKey: String): Observable<List<Video>> =
        this.moviesApiDataSource.getVideos(movieId, apiKey)

}