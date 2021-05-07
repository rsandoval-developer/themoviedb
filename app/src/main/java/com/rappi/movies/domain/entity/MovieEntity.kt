package com.rappi.movies.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "moviesTable")
data class MovieEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "id_movies")
    val idMovies: String,
    @ColumnInfo(name = "overview")
    val overview: String,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String,
    @ColumnInfo(name = "original_title")
    val originalTitle: String,
    @ColumnInfo(name = "video")
    val video: Boolean,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String,
    @ColumnInfo(name = "release_date")
    val releaseDate: Date?,
    @ColumnInfo(name = "popularity")
    val popularity: Double,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "adult")
    val adult: Boolean,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int
)