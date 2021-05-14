package com.rappi.movies.data.datasourceimplementations

import com.rappi.movies.data.db.daos.MoviesDao
import com.rappi.movies.domain.datasource.MoviesDatabaseDataSource
import com.rappi.movies.domain.mappers.MoviesMapper
import com.rappi.movies.domain.model.Movie
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.Observable
import javax.inject.Inject

@ActivityRetainedScoped
class MoviesDatabaseDataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesMapper: MoviesMapper
) :
    MoviesDatabaseDataSource {

    override fun insertMovies(movies: List<Movie>, idMovies: String) {
        moviesDao.insertMovies(moviesMapper.mapToDatabase(movies, idMovies))
    }

    override fun deleteMovies() {
        moviesDao.deleteAllMovies()
    }

    override fun getMovies(idMovies: String): Observable<List<Movie>> =
        moviesDao.getMovies(idMovies)
            .toObservable()
            .map(moviesMapper::mapFromDatabase)

}