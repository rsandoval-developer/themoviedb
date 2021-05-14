package com.rappi.movies.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Movie(
    val id: Int,
    val overview: String,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: Date?,
    val popularity: Double,
    val voteAverage: Double,
    val adult: Boolean,
    val voteCount: Int
) : Parcelable