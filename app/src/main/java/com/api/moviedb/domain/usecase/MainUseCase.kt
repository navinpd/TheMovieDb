package com.api.moviedb.domain.usecase

import javax.inject.Inject

class MainUseCase @Inject constructor(
    val genreUseCase: GenreUseCase,
    val movieDetailUseCase: MovieDetailUseCase,
    val nowPlayingUseCase: NowPlayingUseCase,
    val searchMovieUseCase: SearchMovieUseCase,
    val popularUseCase: PopularUseCase,
    val topRatedUseCase: TopRatedUseCase,
    val upcomingUseCase: UpcomingUseCase
)