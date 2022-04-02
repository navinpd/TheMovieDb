package com.api.moviedb.di

import android.content.Context
import androidx.room.Room
import com.api.moviedb.data.local.dao.MovieDetailsDao
import com.api.moviedb.data.local.db.MovieDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module()
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context, gson: Gson): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "RssReader"
        )
            .build().apply {
                MovieDatabase.gson = gson
            }
    }

    @Provides
    fun provideChannelDao(appDatabase: MovieDatabase): MovieDetailsDao {
        return appDatabase.movieDao()
    }
}