package com.rappi.movies.data.remote.response

import com.google.gson.annotations.SerializedName

data class VideosResponse(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("results")
    val results: List<VideosResultsItem>? = null
)

data class VideosResultsItem(

    @SerializedName("site")
    val site: String? = null,

    @SerializedName("size")
    val size: Int? = null,

    @SerializedName("iso_3166_1")
    val iso31661: String? = null,

    @SerializedName("name")
    val _name: String? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("type")
    val _type: String? = null,

    @SerializedName("iso_639_1")
    val iso6391: String? = null,

    @SerializedName("key")
    val _key: String? = null

) {
    val name: String
        get() = this._name ?: ""

    val type: String
        get() = this._type ?: ""

    val key: String
        get() = this._key ?: ""
}
