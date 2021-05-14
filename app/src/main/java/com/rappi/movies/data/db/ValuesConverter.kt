package com.rappi.movies.data.db

import androidx.room.TypeConverter
import java.util.*

class ValuesConverter {

    @TypeConverter
    fun fromDateToLong(value: Date?): Long = value?.time ?: -1

    @TypeConverter
    fun fromLongToLDate(value: Long): Date? = if (value > -1) Date(value) else null
}