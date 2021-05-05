package com.rappi.movies.data.remote.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class MoviesResponse(

    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("total_pages")
    val totalPages: Int? = null,

    @SerializedName("results")
    val _results: List<ResultsItem>? = null,

    @SerializedName("total_results")
    val totalResults: Int? = null
) {
    val results: List<ResultsItem>
        get() = this._results ?: emptyList()
}

data class ResultsItem(

    @SerializedName("overview")
    val _overview: String? = null,

    @SerializedName("original_language")
    val _originalLanguage: String? = null,

    @SerializedName("original_title")
    val _originalTitle: String? = null,

    @SerializedName("video")
    val _video: Boolean? = null,

    @SerializedName("title")
    val _title: String? = null,

    @SerializedName("genre_ids")
    val _genreIds: List<Int>? = null,

    @SerializedName("poster_path")
    val _posterPath: String? = null,

    @SerializedName("backdrop_path")
    val _backdropPath: String? = null,

    @SerializedName("release_date")
    val _releaseDate: Date? = null,

    @SerializedName("popularity")
    val _popularity: Double? = null,

    @SerializedName("vote_average")
    val _voteAverage: Double? = null,

    @SerializedName("id")
    val _id: Int? = null,

    @SerializedName("adult")
    val _adult: Boolean? = null,

    @SerializedName("vote_count")
    val _voteCount: Int? = null
) {


    val overview: String
        get() = this._overview ?: ""

    val originalLanguage: String
        get() = this._originalLanguage ?: ""

    val originalTitle: String
        get() = this._originalTitle ?: ""

    val video: Boolean
        get() = this._video ?: false

    val title: String
        get() = this._title ?: ""

    val genreIds: List<Int>
        get() = this._genreIds ?: emptyList()

    val posterPath: String
        get() = this._posterPath ?: ""

    val backdropPath: String
        get() = this._backdropPath ?: ""

    val releaseDate: Date
        get() = this._releaseDate ?: Date()

    val popularity: Double
        get() = this._popularity ?: 0.0

    val voteAverage: Double
        get() = this._voteAverage ?: 0.0

    val id: Int
        get() = this._id ?: 0

    val adult: Boolean
        get() = this._adult ?: false

    val voteCount: Int
        get() = this._voteCount ?: 0

}
