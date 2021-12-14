package com.example.core.repository

import com.example.core.api.service.TheMovieAPI
import com.example.core.models.api.DiscoverMovie
import com.example.core.models.api.PaginationResult
import io.reactivex.rxjava3.core.Observable
import org.koin.core.component.KoinComponent

class DiscoverRepository(
    private val service: TheMovieAPI.DiscoverAPI,
) : KoinComponent {

    fun getDiscoverMovies(page: Int, genres: String, sort: String): Observable<PaginationResult<DiscoverMovie>> {
        return service.getMovies(page, genres, sort)
    }
}