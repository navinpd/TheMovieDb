package com.api.moviedb.di

//@Module()
//@InstallIn(SingletonComponent::class)
//class DatabaseModule {
//
//    @Provides
//    @Singleton
//    fun provideAppDatabase(@ApplicationContext appContext: Context): MovieDatabase {
//        return Room.databaseBuilder(
//            appContext,
//            MovieDatabase::class.java,
//            "RssReader"
//        ).build()
//    }
//
//    @Provides
//    fun provideChannelDao(appDatabase: MovieDatabase): MovieDetailsDao {
//        return appDatabase.movieDao()
//    }
//
//}