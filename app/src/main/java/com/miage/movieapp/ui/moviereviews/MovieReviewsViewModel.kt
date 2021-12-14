package com.miage.movieapp.ui.moviereviews

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.models.api.DiscoverMovie
import com.example.core.models.api.Review
import com.example.core.repository.MovieRepository
import com.miage.movieapp.extension.appendList
import com.miage.movieapp.models.MovieParcelable
import com.miage.movieapp.ui.fragment.BaseViewModel

class MovieReviewsViewModel(
    private val repository: MovieRepository
) : BaseViewModel(){

    private var _movieParcelable: MovieParcelable? = null
    var movieParcelable: MovieParcelable?
        get() = _movieParcelable
        set(value) {
            _movieParcelable = value
            reviewsPage.value = 0
            loadMoreReviews()
        }

    private val reviewsPage = MutableLiveData<Int>().apply { value = 1 }

    val reviews = MediatorLiveData<MutableList<Review>>()
    private val _reviews: MutableLiveData<List<Review>> = MutableLiveData()


    init{
        reviews.addSource(_reviews) { it?.let { list -> reviews.appendList(list) } }
    }

    private fun getReviews(page: Int){
        _movieParcelable?.let {
            callSubscribe(repository.getMovieReviews(it.id, page)) {  r -> _reviews.postValue(r) }
        }
    }

    fun loadMoreReviews(){
        reviewsPage.value = reviewsPage.value?.plus(1)
        getReviews(reviewsPage.value!!)
    }

}