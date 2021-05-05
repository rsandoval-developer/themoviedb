package com.rappi.movies.domain.usecases.base

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class ParamsUseCase<Result, Params>() : UseCase<Result, Params>() {

    internal abstract fun createObservable(params: Params): Observable<Result>

    fun execute(
        params: Params,
        onSuccess: (t: Result) -> Unit,
        onError: (t: Throwable) -> Unit
    ) {

        disposeLast()
        lastDisposable = createObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(onSuccess, onError)

        lastDisposable?.let {
            compositeDisposable.add(it)
        }
    }

}