package com.rappi.movies.utils

import android.os.Parcel
import java.util.*

fun Parcel.readDate(): Date? {
    val time = this.readLong()
    return if (time > 0) {
        Date(time)
    } else {
        null
    }
}

fun Parcel.readLongArray(): List<Long> {
    val ids: MutableList<Long> = mutableListOf()
    this.readList(ids, MutableList::class.java.classLoader)
    return ids
}

fun Parcel.writeDate(value: Date?) {
    writeLong(value?.time ?: -1)
}
