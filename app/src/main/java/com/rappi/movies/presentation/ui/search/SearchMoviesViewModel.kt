package com.rappi.movies.presentation.ui.search

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rappi.movies.BuildConfig
import com.rappi.movies.domain.model.Movie
import com.rappi.movies.domain.usecases.ParamsSearchMovies
import com.rappi.movies.domain.usecases.SearchMoviesUseCase
import com.rappi.movies.presentation.base.Resource

class SearchMoviesViewModel @ViewModelInject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase
) :
    ViewModel() {

    val resource = MutableLiveData<Resource<List<Movie>>>()

    fun searchMovies(
        query: String
    ) {
        resource.value = Resource.Loading(true)
        searchMoviesUseCase.execute(
            params = ParamsSearchMovies(BuildConfig.API_KEY, query),
            onSuccess = ::handleMovies,
            onError = ::handleError
        )
    }

    private fun handleMovies(counters: List<Movie>) {
        resource.value = Resource.Loading(false)
        resource.value = Resource.Success(counters)
    }

    private fun handleError(error: Throwable) {
        resource.value = Resource.Loading(false)
        resource.value = Resource.Failure(error)
    }

    override fun onCleared() {
        searchMoviesUseCase.dispose()
        super.onCleared()
    }
}