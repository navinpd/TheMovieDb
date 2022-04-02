package com.api.moviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.api.moviedb.data.local.entity.movieDetails.MovieDetailEntity
import io.reactivex.Observable

@Dao
interface MovieDetailsDao {
    @Query("SELECT * FROM Movie_Detail")
    fun getAllLikedMovies(): Observable<Array<MovieDetailEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(movieDetail: MovieDetailEntity)

    @Query("DELETE FROM Movie_Detail WHERE id = :id")
    fun deleteMovieById(id: Int)

}
