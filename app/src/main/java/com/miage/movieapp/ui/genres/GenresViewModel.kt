package com.miage.movieapp.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.models.api.Genre
import com.example.core.models.api.Token
import com.example.core.repository.GenreRepository
import com.example.core.repository.TokenRepository
import com.example.core.utils.ResultApi
import com.miage.movieapp.ui.fragment.BaseViewModel
import com.miage.movieapp.ui.movies.MoviesViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GenresViewModel(
    private val repository: GenreRepository
) : BaseViewModel() {

    private val _categories: MutableLiveData<List<Genre>> = MutableLiveData()
    val categories: LiveData<List<Genre>> get() = _categories

    fun getCategories() {
        callSubscribe(repository.getGenres()) { _categories.postValue(it) }
    }

}