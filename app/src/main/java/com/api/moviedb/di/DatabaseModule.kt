package com.api.moviedb.di

import android.content.Context
import android.os.Debug
import androidx.room.Room
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.local.db.dao.MovieDetailsDao
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module()
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context, gson: Gson): MovieDatabase {
        val builder = Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "MyDatabase.db"
        ).fallbackToDestructiveMigration()
        if (Debug.isDebuggerConnected()) {
            builder.allowMainThreadQueries()
        }
        return builder.build().apply {
            MovieDatabase.gson = gson
        }
    }

    @Provides
    fun provideChannelDao(appDatabase: MovieDatabase): MovieDetailsDao {
        return appDatabase.movieDao()
    }
}