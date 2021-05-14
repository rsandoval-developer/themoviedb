package com.rappi.movies.domain.mappers

import com.rappi.movies.data.api.response.VideosResponse
import com.rappi.movies.domain.model.Video
import javax.inject.Inject

class VideosMapper @Inject constructor() {

    fun mapFromApi(videoResponse: VideosResponse): List<Video> =
        videoResponse.results.map { video ->
            Video(
                video.name,
                video.type,
                video.key
            )
        }


}