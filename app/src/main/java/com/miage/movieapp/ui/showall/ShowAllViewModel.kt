package com.miage.movieapp.ui.showall

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.models.api.Movie
import com.example.core.models.api.PaginationResult
import com.example.core.repository.MovieRepository
import com.miage.movieapp.extension.appendList
import com.miage.movieapp.models.ShowAllType
import com.miage.movieapp.ui.fragment.BaseViewModel
import io.reactivex.rxjava3.core.Observable

class ShowAllViewModel(
    private val repository: MovieRepository
) : BaseViewModel() {

    private val moviesPage = MutableLiveData<Int>().apply { value = 1 }

    val movieList = MediatorLiveData<MutableList<Movie>>()
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()

    private var _type: ShowAllType? = null

    var type : ShowAllType?
        get() = _type
        set(value) {
            _type = value
            getMovies()
        }

    init {
        movieList.addSource(_movies) { it?.let { list -> movieList.appendList(list) } }
    }

    private fun getMovies(){
        selectTypeObservable()?.let {
            callSubscribe(it(moviesPage.value!!)) {  movies -> _movies.postValue(movies.results) }
        }
    }

    private fun selectTypeObservable() : ((page: Int) -> Observable<PaginationResult<Movie>>)? {
        when(_type){
            ShowAllType.THEATER -> return repository::getNowPlayingMovies
            ShowAllType.POPULAR -> return repository::getPopularMovies
            ShowAllType.UPCOMING -> return repository::getUpcomingMovies
            ShowAllType.TOP_RATED -> return repository::getTopRatedMovies
        }
        return null;
    }

    fun loadMoreMovies() {
        moviesPage.value = moviesPage.value?.plus(1)
        getMovies()
    }

}