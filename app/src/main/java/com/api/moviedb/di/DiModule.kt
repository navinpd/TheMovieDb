package com.api.moviedb.di

import android.content.Context
import androidx.room.Room
import com.api.moviedb.BuildConfig
import com.api.moviedb.data.local.dao.MovieDetailsDao
import com.api.moviedb.data.local.db.MovieDatabase
import com.api.moviedb.data.remote.api.MovieApi
import com.api.moviedb.data.repository.MovieRepositoryImpl
import com.api.moviedb.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module()
@InstallIn(SingletonComponent::class)
class DiModule {

    private companion object {
        const val BASE_URL = "https://api.themoviedb.org/"
        private val client = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    })
            .build()
    }

    @Reusable
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    @Reusable
    @Provides
    fun provideMovieApi(
        retrofit: Retrofit
    ): MovieApi {
        return retrofit.create(MovieApi::class.java)
    }

    @Provides
    fun provideMovieRepository(
        movieApi: MovieApi,
        movieDetailsDao: MovieDetailsDao
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieApi = movieApi,
            dbApi = movieDetailsDao
        )
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(
            appContext,
            MovieDatabase::class.java,
            "movie_database.db"
        ).build()
    }

    @Provides
    fun provideChannelDao(appDatabase: MovieDatabase): MovieDetailsDao {
        return appDatabase.movieDao()
    }

}