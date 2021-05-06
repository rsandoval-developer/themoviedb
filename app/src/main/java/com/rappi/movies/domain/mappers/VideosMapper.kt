package com.rappi.movies.domain.mappers

import com.rappi.movies.data.remote.response.VideosResultsItem
import com.rappi.movies.domain.model.Video
import javax.inject.Inject

class VideosMapper @Inject constructor() {

    fun mapFromApi(video: VideosResultsItem): Video =
        Video(
            video.name,
            video.type,
            video.key
        )

}