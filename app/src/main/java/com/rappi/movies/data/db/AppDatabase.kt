package com.rappi.movies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.rappi.movies.data.db.daos.MoviesDao
import com.rappi.movies.domain.entity.MovieEntity


@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(ValuesConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun MoviesDao(): MoviesDao
}