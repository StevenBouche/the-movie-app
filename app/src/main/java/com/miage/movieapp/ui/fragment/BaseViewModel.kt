package com.miage.movieapp.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel: ViewModel() {

    private val disposables: CompositeDisposable = CompositeDisposable()

    protected val _error: MutableLiveData<Throwable> = MediatorLiveData()

    val error: LiveData<Throwable>
        get() = _error

    protected fun <T> callSubscribe(observable: Observable<T>, onNext: (T) -> Unit): Disposable {
        val d = observable.subscribe(onNext, { _error.postValue(it) })
        disposables.add(d)
        return d
    }

    override fun onCleared() {
        disposables.clear()
    }

}