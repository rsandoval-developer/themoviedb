package com.rappi.movies.domain.mappers

import com.rappi.movies.data.api.response.MoviesResponse
import com.rappi.movies.domain.entity.MovieEntity
import com.rappi.movies.domain.model.Movie
import javax.inject.Inject

class MoviesMapper @Inject constructor() {

    fun mapFromApi(moviesResponse: MoviesResponse): List<Movie> =
        moviesResponse.results.map { movie ->
            Movie(
                movie.id,
                movie.overview,
                movie.originalLanguage,
                movie.originalTitle,
                movie.video,
                movie.title,
                movie.posterPath.orEmpty(),
                movie.backdropPath.orEmpty(),
                movie.releaseDate,
                movie.popularity,
                movie.voteAverage,
                movie.adult,
                movie.voteCount
            )
        }


    fun mapToDatabase(movies: List<Movie>, idMovies: String): List<MovieEntity> =
        movies.map { movie ->
            MovieEntity(
                movie.id,
                idMovies,
                movie.overview,
                movie.originalLanguage,
                movie.originalTitle,
                movie.video,
                movie.title,
                movie.posterPath,
                movie.backdropPath,
                movie.releaseDate,
                movie.popularity,
                movie.voteAverage,
                movie.adult,
                movie.voteCount
            )
        }


    fun mapFromDatabase(movies: List<MovieEntity>): List<Movie> =
        movies.map { movie ->
            Movie(
                movie.id,
                movie.overview,
                movie.originalLanguage,
                movie.originalTitle,
                movie.video,
                movie.title,
                movie.posterPath,
                movie.backdropPath,
                movie.releaseDate,
                movie.popularity,
                movie.voteAverage,
                movie.adult,
                movie.voteCount
            )
        }
}