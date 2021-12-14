package com.miage.movieapp.ui.home

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.models.api.Movie
import com.example.core.repository.MovieRepository
import com.miage.movieapp.extension.appendList
import com.miage.movieapp.ui.fragment.BaseViewModel

class HomeViewModel(
    private val repository : MovieRepository
): BaseViewModel() {

    private val popularPage = MutableLiveData<Int>().apply { value = 0 }
    private val nowPlayingPage = MutableLiveData<Int>().apply { value = 0 }
    private val upcomingPage = MutableLiveData<Int>().apply { value = 0 }
    private val topRatedPage = MutableLiveData<Int>().apply { value = 0 }

    private val _popularMovies:  MutableLiveData<List<Movie>>  = MutableLiveData()
    private val _inTheatersMovies:  MutableLiveData<List<Movie>> = MutableLiveData()
    private val _upcomingMovies:  MutableLiveData<List<Movie>> = MutableLiveData()
    private val _topMovies:  MutableLiveData<List<Movie>> = MutableLiveData()

    val popularMovieList = MediatorLiveData<MutableList<Movie>>()
    val inTheatersMovieList = MediatorLiveData<MutableList<Movie>>()
    val upcomingMovieList = MediatorLiveData<MutableList<Movie>>()
    val topMovieList = MediatorLiveData<MutableList<Movie>>()

    val highlightedMovie = MediatorLiveData<Movie>()

    init {

        popularMovieList.addSource(_popularMovies) {
            it?.let { list -> popularMovieList.appendList(list) }
        }

        inTheatersMovieList.addSource(_inTheatersMovies) {
            it?.let { list -> inTheatersMovieList.appendList(list) }
        }

        upcomingMovieList.addSource(_upcomingMovies) {
            it?.let { list -> upcomingMovieList.appendList(list) }
        }

        topMovieList.addSource(_topMovies) {
            it?.let { list -> topMovieList.appendList(list) }
        }

        highlightedMovie.addSource(_popularMovies) {
            if (highlightedMovie.value == null) {
                highlightedMovie.value = it?.first()
            }
        }
    }

    private fun getPopularMovies(page: Int){
        callSubscribe(repository.getPopularMovies(page)) { movies -> _popularMovies.postValue(movies.results) }
    }

    private fun getNowPlayingMovies(page: Int){
        callSubscribe(repository.getNowPlayingMovies(page)) { movies ->  _inTheatersMovies.postValue(movies.results) }
    }

    private fun getUpcomingMovies(page: Int){
        callSubscribe(repository.getUpcomingMovies(page)) { movies -> _upcomingMovies.postValue(movies.results) }
    }

    private fun getTopRatedMovies(page: Int){
        callSubscribe(repository.getTopRatedMovies(page)) { movies -> _topMovies.postValue(movies.results) }
    }

    fun loadMorePopular() {
        popularPage.value = popularPage.value?.plus(1)
        popularPage.value?.let { getPopularMovies(it) }
    }

    fun loadMoreNowPlaying() {
        nowPlayingPage.value = nowPlayingPage.value?.plus(1)
        nowPlayingPage.value?.let { getNowPlayingMovies(it) }
    }

    fun loadMoreUpcoming() {
        upcomingPage.value = upcomingPage.value?.plus(1)
        upcomingPage.value?.let { getUpcomingMovies(it) }
    }

    fun loadMoreTopRated() {
        topRatedPage.value = topRatedPage.value?.plus(1)
        topRatedPage.value?.let { getTopRatedMovies(it) }
    }

}