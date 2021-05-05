package com.rappi.movies.data.remote.services


import com.rappi.movies.AppConstants.API_MOVIES
import com.rappi.movies.AppConstants.API_SEARCH_MOVIES
import com.rappi.movies.data.remote.response.MoviesResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesServices {

    @GET(API_MOVIES)
    fun getMovies(
        @Path("id") movieId: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Observable<Response<MoviesResponse>>

    @GET(API_SEARCH_MOVIES)
    fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): Observable<Response<MoviesResponse>>

}