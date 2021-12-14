package com.example.core.repository

import com.example.core.api.service.TheMovieAPI
import com.example.core.models.api.Genre
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent

class GenreRepository(
    private val service: TheMovieAPI.GenresAPI,
) : KoinComponent {

    fun getGenres(): Observable<List<Genre>> {
        return service.getGenresMoviesObservable()
            .map { it.genres }
    }

}