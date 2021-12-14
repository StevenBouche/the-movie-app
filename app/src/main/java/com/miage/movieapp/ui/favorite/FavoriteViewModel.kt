package com.miage.movieapp.ui.favorite

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.models.entity.MovieFavoriteEntity
import com.example.core.repository.MovieRepository
import com.miage.movieapp.ui.fragment.BaseViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

class FavoriteViewModel(
    private val repository: MovieRepository
) : BaseViewModel() {

    val movieList = MediatorLiveData<List<MovieFavoriteEntity>>()
    private val _movies: MutableLiveData<List<MovieFavoriteEntity>> = MutableLiveData()

    init{
        movieList.addSource(_movies) {
            movieList.postValue(it)
        }
    }

    fun getMovieFavorites(){
        callSubscribe(repository.getAllMovieFavorite()) { _movies.postValue(it) }
    }
}