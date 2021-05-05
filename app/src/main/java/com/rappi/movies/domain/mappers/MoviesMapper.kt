package com.rappi.movies.domain.mappers

import com.rappi.movies.data.remote.response.ResultsItem
import com.rappi.movies.domain.entity.MovieEntity
import com.rappi.movies.domain.model.Movie
import javax.inject.Inject

class MoviesMapper @Inject constructor() {

    fun mapFromApi(movie: ResultsItem): Movie =
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


    fun mapToDatabase(movie: Movie, idMovies: String): MovieEntity =
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

    fun mapFromDatabase(movie: MovieEntity): Movie =
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