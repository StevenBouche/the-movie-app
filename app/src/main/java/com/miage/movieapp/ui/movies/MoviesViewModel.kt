package com.miage.movieapp.ui.movies

import androidx.lifecycle.*
import com.example.core.models.api.DiscoverMovie
import com.example.core.models.api.Genre
import com.example.core.repository.DiscoverRepository
import com.miage.movieapp.extension.appendList
import com.miage.movieapp.models.DiscoverSortBy
import com.miage.movieapp.ui.fragment.BaseViewModel
import io.reactivex.rxjava3.disposables.Disposable

class MoviesViewModel(
    private val repository: DiscoverRepository
) : BaseViewModel() {

    //pagination
    private val moviesPage = MutableLiveData<Int>().apply { value = 1 }

    //live data
    val movieList = MediatorLiveData<MutableList<DiscoverMovie>>()
    private val _movies: MutableLiveData<List<DiscoverMovie>> = MutableLiveData()

    //region inputsVariable
    private var _genre: Genre? = null
    private var _sortType: DiscoverSortBy? = null
    val sortType: DiscoverSortBy? get() = _sortType
    val genre: Genre? get() = _genre
    //endregion

    private var currentQuery: Disposable? = null

    init {
        movieList.addSource(_movies) { it?.let { list -> movieList.appendList(list) } }
    }

    fun setGenre(value: Genre, onLoad: () -> Unit){
        if(genre == null || genre!!.id != value.id){
            _genre = value
            refresh(onLoad)
        }
    }

    fun setSortType(value: DiscoverSortBy, onLoad: () -> Unit){
        if(_sortType == null || _sortType!!.id != value.id){
            _sortType = value
            refresh(onLoad)
        }
    }

    private fun refresh(onLoad: () -> Unit){
        moviesPage.value = 1
        movieList.value?.clear()
        onLoad()
        getMovies()
    }

    private fun getMovies(){
        _genre?.let {
            val sortStr = sortType?.value ?: DiscoverSortBy.POPULARITY_DESC.value
            val call = repository.getDiscoverMovies(moviesPage.value!!, it.id.toString(), sortStr)
            currentQuery?.dispose()
            currentQuery = callSubscribe(call) {  movies -> _movies.postValue(movies.results) }
        }
    }

    fun loadMoreMovies() {
        moviesPage.value = moviesPage.value?.plus(1)
        getMovies()
    }
}