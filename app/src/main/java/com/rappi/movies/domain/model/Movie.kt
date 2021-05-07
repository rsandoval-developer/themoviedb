package com.rappi.movies.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.rappi.movies.extensions.readDate
import com.rappi.movies.extensions.writeDate
import java.util.*

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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().orEmpty() ,
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty(),
        parcel.readDate(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(overview)
        parcel.writeString(originalLanguage)
        parcel.writeString(originalTitle)
        parcel.writeByte(if (video) 1 else 0)
        parcel.writeString(title)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
        parcel.writeDate(releaseDate)
        parcel.writeDouble(popularity)
        parcel.writeDouble(voteAverage)
        parcel.writeByte(if (adult) 1 else 0)
        parcel.writeInt(voteCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }

}