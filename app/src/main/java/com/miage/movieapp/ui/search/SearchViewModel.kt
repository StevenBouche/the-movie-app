package com.miage.movieapp.ui.search

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.models.api.DiscoverMovie
import com.example.core.repository.MovieRepository
import com.miage.movieapp.extension.appendList
import com.miage.movieapp.ui.fragment.BaseViewModel
import io.reactivex.rxjava3.disposables.Disposable

class SearchViewModel(
    private val repository: MovieRepository
    ): BaseViewModel() {

    private var currentQuery: Disposable? = null

    private var query: String? = null
    private val moviesPage = MutableLiveData<Int>().apply { value = 1 }

    val movieList = MediatorLiveData<MutableList<DiscoverMovie>>()
    private val _movies: MediatorLiveData<List<DiscoverMovie>> = MediatorLiveData<List<DiscoverMovie>>()

    val page: Int?
        get() = moviesPage.value

    init {
        _movies.addSource(moviesPage) {
            loadMoviesSearch(it)
        }
        movieList.addSource(_movies) {
            it?.let { list -> movieList.appendList(list) }
        }
    }

    fun getMoviesSearch(query: String, onLoad: () -> Unit) {
        if(query.isNotEmpty() && !this.query.equals(query)){
            onLoad()
            movieList.value?.clear()
            this.query = query
            moviesPage.postValue(1)
        }
    }

    private fun loadMoviesSearch(page: Int){
        query?.let { it ->
            currentQuery?.dispose()
            currentQuery = callSubscribe(repository.getMoviesSearch(page, it)) {  movies -> _movies.postValue(movies.results)}
        }
    }

    fun loadMoreMovies() {
        moviesPage.value = moviesPage.value?.plus(1)
    }
}