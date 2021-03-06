package com.rappi.movies.domain.repository

import com.rappi.movies.data.api.request.GetMoviesRequestParams
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.model.Video
import io.reactivex.Observable

interface MoviesRepository {

    fun getMovies(params: GetMoviesRequestParams): Observable<List<Movie>>
    fun searchMovies(apiKey: String, query: String): Observable<List<Movie>>
    fun getVideos(movieId: Int, apiKey: String): Observable<List<Video>>

}