package com.api.moviedb.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.api.moviedb.BuildConfig
import com.api.moviedb.data.local.db.dao.MovieDetailsDao
import com.api.moviedb.data.local.db.mapper.MovieDetailDataToEntityMapper
import com.api.moviedb.data.local.db.mapper.MovieEntityToDataMapper
import com.api.moviedb.data.local.db.mapper.MovieListEntityToMovieListDataMapper
import com.api.moviedb.data.remote.api.MovieApi
import com.api.moviedb.data.repository.MovieRepositoryImpl
import com.api.moviedb.domain.repository.MovieRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
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
        const val SHARED_PREFERENCE = "LOCAL_PREFERENCE"
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
        movieDetailsDao: MovieDetailsDao,
        movieDataToEntityMapper: MovieDetailDataToEntityMapper,
        movieListEntityToDataMapper: MovieListEntityToMovieListDataMapper,
        movieEntityToDataMapper: MovieEntityToDataMapper
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieApi = movieApi,
            dbApi = movieDetailsDao,
            movieDataToEntityMapper = movieDataToEntityMapper,
            movieListEntityToDataMapper = movieListEntityToDataMapper,
            movieEntityToDataMapper = movieEntityToDataMapper
        )
    }

    @Provides
    fun provideGlide(@ApplicationContext appContext: Context): RequestManager {
        return Glide.with(appContext)
    }

    @Provides
    fun provideSharedPref(@ApplicationContext appContext: Context) : SharedPreferences {
        return appContext.getSharedPreferences(SHARED_PREFERENCE, MODE_PRIVATE)
    }
}