package com.miage.movieapp.ui.moviedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.models.api.*
import com.example.core.models.entity.MovieFavoriteEntity
import com.example.core.repository.MovieRepository
import com.miage.movieapp.models.MovieParcelable
import com.miage.movieapp.ui.fragment.BaseViewModel

class MovieDetailsViewModel(
    private val repository: MovieRepository
) : BaseViewModel()
{
    private var _movieParcelable: MovieParcelable? = null

    private val _account: MutableLiveData<AccountMovie> = MutableLiveData()
    private val _movie: MutableLiveData<Movie> = MutableLiveData()
    private val _videos: MutableLiveData<List<MovieVideo>> = MutableLiveData()
    private val _casts: MutableLiveData<List<Cast>> = MutableLiveData()

    var movieParcelable: MovieParcelable? get() = _movieParcelable
        set(value) {
            _movieParcelable = value
            getMovie()
        }

    val movie: LiveData<Movie> get() = _movie
    val videos: LiveData<List<MovieVideo>> get() = _videos
    val casts: LiveData<List<Cast>> get() = _casts
    val account: LiveData<AccountMovie> get() = _account
    val movieFavorite: MediatorLiveData<MovieFavoriteEntity?> = MediatorLiveData<MovieFavoriteEntity?>()

    init {
        movieFavorite.addSource(_movie) {
            callSubscribe(repository.getMovieFavorite(it.id)) {f -> movieFavorite.postValue(f) }
        }
    }

    fun actionFavorite(){
        if(movieFavorite.value == null){
            _movie.value?.let {
                val movie = MovieFavoriteEntity(it)
                repository.setMovieFavorite(movie)
            }
        }
        else {
            val movie = movieFavorite.value!!
            repository.deleteMovieFavorite(movie.id)
                .subscribe {
                    movieFavorite.postValue(null)
                }
        }
    }

    private fun getMovie() {
        _movieParcelable?.let {
            callSubscribe(repository.getMovieDetails(it.id)) { m -> _movie.postValue(m) }
            callSubscribe(repository.getMovieVideos(it.id)) { v -> _videos.postValue(v) }
            callSubscribe(repository.getMovieCredits(it.id)) { c -> _casts.postValue(c) }
            callSubscribe(repository.getAccountMovie(it.id)) { a -> _account.postValue(a) }
        }
    }

    fun rateMovie(value: Float) {
        _movieParcelable?.let {
            callSubscribe(repository.postRateMovie(it.id, Rated(value))) { response ->
                if(response.code == 1) {
                    callSubscribe(repository.getMovieDetails(it.id)) { m -> _movie.postValue(m) }
                    callSubscribe(repository.getAccountMovie(it.id)) { a -> _account.postValue(a) }
                }
            }
        }
    }
}