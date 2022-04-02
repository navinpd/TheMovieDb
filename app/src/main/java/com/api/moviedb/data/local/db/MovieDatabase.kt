package com.api.moviedb.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.api.moviedb.data.local.dao.MovieDetailsDao
import com.api.moviedb.data.local.entity.movieDetails.MovieDetailEntity
import com.api.moviedb.data.local.typerConvertor.*
import javax.inject.Singleton

@Database(entities = [MovieDetailEntity::class], version = 1, exportSchema = false)
@TypeConverters(
    GenreTypeConvertor::class,
    ProdCountryTypeConvertor::class,
    ProdCompanyTypeConvertor::class,
    SpokenLangTypeConvertor::class
)
@Singleton
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDetailsDao

}