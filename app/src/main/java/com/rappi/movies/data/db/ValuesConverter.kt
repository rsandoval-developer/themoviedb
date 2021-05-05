package com.rappi.movies.data.db

import androidx.room.TypeConverter
import java.util.*

class ValuesConverter {

    @TypeConverter
    fun fromListOfStringsToString(value: List<String>?): String = value?.toString() ?: ""

    @TypeConverter
    fun fromStringToListOfString(value: String?): List<String> {
        return if (value?.isBlank() == true || value?.equals("[]") == true) {
            emptyList()
        } else {
            value?.replace("[", "")
                    ?.replace("]", "")
                    ?.trim()
                    ?.split(",") ?: emptyList()
        }
    }

    @TypeConverter
    fun fromListOfLongToString(value: List<Long>?): String = value?.toString() ?: ""

    @TypeConverter
    fun fromStringToListOfLong(value: String?): List<Long> {
        return if (value?.isBlank() == true || value?.equals("[]") == true) {
            emptyList()
        } else {
            value?.replace("[", "")
                    ?.replace("]", "")
                    ?.trim()
                    ?.split(",")
                    ?.map {
                        it.trim().toLong()
                    } ?: emptyList()
        }
    }

    @TypeConverter
    fun fromDateToLong(value: Date?): Long = value?.time ?: -1

    @TypeConverter
    fun fromLongToLDate(value: Long): Date? = if (value > -1) Date(value) else null
}