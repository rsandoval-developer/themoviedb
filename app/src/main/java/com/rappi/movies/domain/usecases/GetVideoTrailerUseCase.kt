package com.rappi.movies.domain.usecases

import com.rappi.movies.BuildConfig
import com.rappi.movies.domain.model.Video
import com.rappi.movies.domain.repository.MoviesRepository
import com.rappi.movies.domain.usecases.base.ParamsUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetVideoTrailerUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) :
    ParamsUseCase<Video, Int>() {

    override fun createObservable(params: Int): Observable<Video> =
        moviesRepository.getVideos(params, BuildConfig.API_KEY).flatMapIterable { it }
            .filter { video -> video.type == "Trailer" }

}