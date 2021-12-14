package com.example.core.repository

import com.example.core.api.service.TheMovieAPI
import com.example.core.local.daos.MovieFavoriteDao
import com.example.core.models.api.*
import com.example.core.models.entity.MovieFavoriteEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * La classe permettant de gérer les données de l'application
 */
class MovieRepository(
    private val service: TheMovieAPI.MoviesAPI
) : KoinComponent {

    private val movieFavoriteDao : MovieFavoriteDao by inject()

    fun getMovieDetails(movieId: Int): Observable<Movie> {
        return service.getMovieDetails(movieId)
    }

    fun getMovieVideos(movieId: Int): Observable<List<MovieVideo>>{
        return service.getMovieVideos(movieId)
            .map { it.results }
    }

    fun getMovieReviews(movieId: Int, page: Int): Observable<List<Review>>{
        return service.getMovieReviews(movieId, page)
            .map { it.results }
    }

    fun getMovieCredits(movieId: Int): Observable<List<Cast>>{
        return service.getMovieCredits(movieId)
            .map { it.results }
    }

    fun getPopularMovies(page: Int): Observable<PaginationResult<Movie>> {
        return service.getPopularMovies(page)
    }

    fun getUpcomingMovies(page: Int): Observable<PaginationResult<Movie>> {
        return service.getUpcomingMovies(page)
    }

    fun getNowPlayingMovies(page: Int): Observable<PaginationResult<Movie>> {
        return service.getNowPlayingMovies(page)
    }

    fun getTopRatedMovies(page: Int): Observable<PaginationResult<Movie>> {
        return service.getTopRatedMovies(page)
    }

    fun getMoviesSearch(page: Int, query: String ): Observable<PaginationResult<DiscoverMovie>> {
        return service.getMoviesSearch(page, query)
    }

    fun getLatestMovies(page: Int): Observable<PaginationResult<Movie>> {
        return service.getLatestMovies(page)
    }

    fun setMovieFavorite(movie : MovieFavoriteEntity){
        movieFavoriteDao.insert(movie)
    }

    fun deleteMovieFavorite(value: Int) : Completable {
        return movieFavoriteDao.delete(value)
    }

    fun getMovieFavorite(id: Int) : Observable<MovieFavoriteEntity> {
        return movieFavoriteDao.retrieve(id)
    }

    fun getAllMovieFavorite() : Observable<List<MovieFavoriteEntity>> {
        return movieFavoriteDao.retrieveAll()
    }

    fun getAccountMovie(movieId: Int): Observable<AccountMovie>{
        return service.getAccountMovie(movieId)
    }

    fun postRateMovie(movieId: Int, rate: Rated): Observable<RatingResponse>{
        return service.postRateMovie(movieId, rate);
    }

}