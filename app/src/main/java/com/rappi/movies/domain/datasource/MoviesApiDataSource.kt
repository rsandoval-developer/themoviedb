package com.rappi.movies.domain.datasource

import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.model.Video
import io.reactivex.Observable

interface MoviesApiDataSource {

    fun getMovies(moviesId: String, apiKey: String, page: Int): Observable<List<Movie>>
    fun searchMovies(apiKey: String, query: String): Observable<List<Movie>>
    fun getVideos(movieId: Int, apiKey: String): Observable<List<Video>>

}