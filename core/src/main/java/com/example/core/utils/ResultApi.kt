package com.example.core.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.core.Observable

sealed class ResultApi<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResultApi<T>()
    data class Error(
        val exception: Throwable,
        val code: Int,
        val message: String
    ) : ResultApi<Nothing>()
}

fun <T : Any> buildObservable(o: Observable<T>): Observable<T>{
    return o.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}