package com.rappi.movies.data.db.daos

import androidx.room.*
import com.rappi.movies.domain.entity.MovieEntity
import io.reactivex.Single

@Dao
interface MoviesDao {

    @Query(value = "SELECT * FROM moviesTable")
    fun getAllMovies(): Single<List<MovieEntity>>

    @Query(value = "SELECT * FROM moviesTable WHERE id_movies = :idMovies")
    fun getMovies(idMovies: String): Single<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Delete
    fun deleteMovie(movie: MovieEntity)

    @Query("DELETE FROM moviesTable")
    fun deleteAllMovies()

}