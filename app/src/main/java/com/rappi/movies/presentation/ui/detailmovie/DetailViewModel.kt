package com.rappi.movies.presentation.ui.detailmovie

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rappi.movies.domain.model.Video
import com.rappi.movies.domain.usecases.GetVideoTrailerUseCase
import com.rappi.movies.presentation.base.Resource

class DetailViewModel @ViewModelInject constructor(
    private val getVideoTrailerUseCase: GetVideoTrailerUseCase
) :
    ViewModel() {

    val resource = MutableLiveData<Resource<Video>>()

    fun getVideoTrailer(
        movieId: Int
    ) {
        getVideoTrailerUseCase.execute(
            params = movieId,
            onSuccess = ::handleVideoTrailer,
            onError = ::handleError
        )
    }

    private fun handleVideoTrailer(video: Video) {
        resource.value = Resource.Loading(false)
        resource.value = Resource.Success(video)
    }

    private fun handleError(error: Throwable) {
        resource.value = Resource.Loading(false)
        resource.value = Resource.Failure(error)
    }

    override fun onCleared() {
        getVideoTrailerUseCase.dispose()
        super.onCleared()
    }
}