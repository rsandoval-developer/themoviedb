package com.rappi.movies.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Video(
    val name: String,
    val type: String,
    val key: String
) : Parcelable