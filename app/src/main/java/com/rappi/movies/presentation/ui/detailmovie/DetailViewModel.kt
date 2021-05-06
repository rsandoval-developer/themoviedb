package com.rappi.movies.presentation.ui.detailmovie

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.rappi.movies.domain.model.Video
import com.rappi.movies.domain.usecases.GetVideoTrailerUseCase
import com.rappi.movies.presentation.base.Resource
import com.rappi.movies.presentation.base.ViewModelBase

class DetailViewModel @ViewModelInject constructor(
    private val getVideoTrailerUseCase: GetVideoTrailerUseCase,
    application: Application
) :
    ViewModelBase(application) {

    val resource = MutableLiveData<Resource<Video>>()

    fun getVideoTrailer(
        movieId: Int
    ) {
        this.getVideoTrailerUseCase.execute(
            params = movieId,
            onSuccess = ::handleVideoTrailer,
            onError = ::handleError
        )
    }

    private fun handleVideoTrailer(video: Video) {
        this.resource.value = Resource.Loading(false)
        this.resource.value = Resource.Success(video)
    }

    override fun defaultError(error: Throwable) {
        this.resource.value = Resource.Loading(false)
        this.resource.value = Resource.Failure(error)
    }

    override fun onCleared() {
        this.getVideoTrailerUseCase.dispose()
        super.onCleared()
    }
}