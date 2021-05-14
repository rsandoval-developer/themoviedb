package com.rappi.movies.data.api.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class MoviesResponse(

    @SerializedName("page")
    val page: Int,

    @SerializedName("total_pages")
    val totalPages: Int,

    @SerializedName("results")
    val results: List<ResultsItem>,

    @SerializedName("total_results")
    val totalResults: Int
)

data class ResultsItem(

    @SerializedName("overview")
    val overview: String,

    @SerializedName("original_language")
    val originalLanguage: String,

    @SerializedName("original_title")
    val originalTitle: String,

    @SerializedName("video")
    val video: Boolean,

    @SerializedName("title")
    val title: String,

    @SerializedName("genre_ids")
    val genreIds: List<Int>,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("release_date")
    val releaseDate: Date?,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("id")
    val id: Int,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("vote_count")
    val voteCount: Int
)