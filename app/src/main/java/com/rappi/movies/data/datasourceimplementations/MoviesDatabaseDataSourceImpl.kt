package com.rappi.movies.data.datasourceimplementations

import android.annotation.SuppressLint
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

    @SuppressLint("CheckResult")
    override fun insertMovies(movies: List<Movie>, idMovies: String) {
        Observable.fromArray(movies)
            .flatMapIterable { countersIterable -> countersIterable }
            .map { movie -> this.moviesMapper.mapToDatabase(movie, idMovies) }
            .toList()
            .toObservable()
            .subscribe { countersEntities ->
                this.moviesDao.insertCounters(countersEntities)
            }
    }

    override fun deleteMovies() {
        this.moviesDao.deleteAllMovies()
    }

    override fun getMovies(idMovies: String): Observable<List<Movie>> =
        this.moviesDao.getMovies(idMovies).toObservable()
            .flatMapIterable { it }
            .map(this.moviesMapper::mapFromDatabase)
            .toList()
            .toObservable()
}