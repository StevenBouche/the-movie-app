package com.example.core.api.service

import com.example.core.api.response.CreditsResponse
import com.example.core.api.response.GenresResponse
import com.example.core.api.response.MovieVideosResponse
import com.example.core.models.api.*
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.*

object TheMovieAPI {

    private const val API_VERSION: Int = 3
    private const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w185"
    private const val BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780"
    private const val BASE_PROFILE_URL = "https://image.tmdb.org/t/p/w185"
    private const val BASE_YT_IMG_URL = "https://img.youtube.com/vi/"
    private const val BASE_YT_WATCH_URL = "https://www.youtube.com/watch?v="
    private const val BASE_AVATAR_URL = "https://image.tmdb.org/t/p/w150_and_h150_face/"
    const val BASE_API_URL = "https://api.themoviedb.org/"
    const val MAX_RATING = 10f

    fun getPosterUrl(path: String) = BASE_POSTER_URL + path
    fun getAvatarUrl(path: String): String {
        return if(path.contains("https://secure.gravatar.com/avatar/"))
            path.drop(1)
        else
            BASE_AVATAR_URL + path;
    }
    fun getBackdropUrl(path: String) = BASE_BACKDROP_URL + path
    fun getProfileUrl(path: String) = BASE_PROFILE_URL + path
    fun getYoutubeImageUrl(youtubeId: String) = "$BASE_YT_IMG_URL$youtubeId/hqdefault.jpg"
    fun getYoutubeWatchUrl(youtubeId: String) = "$BASE_YT_WATCH_URL$youtubeId"/**/
    fun getRuntimeDateFormat() = ("yyyy-MM-dd")

    interface DiscoverAPI {

        @GET("discover/movie")
        fun getMovies(@Query("page") page: Int, @Query("with_genres") genres: String, @Query("sort_by") sort: String): Observable<PaginationResult<DiscoverMovie>>
    }

    interface GenresAPI {

        @GET("genre/movie/list")
        suspend fun getGenresMovies(): Response<GenresResponse>

        @GET("genre/movie/list")
        fun getGenresMoviesObservable(): Observable<GenresResponse>
    }

    interface MoviesAPI {

        @GET("movie/{id}")
        fun getMovieDetails(@Path("id") id: Int): Observable<Movie>

        @GET("movie/{id}/videos")
        fun getMovieVideos(@Path("id") id: Int): Observable<MovieVideosResponse>

        @GET("movie/{id}/credits")
        fun getMovieCredits(@Path("id") id: Int): Observable<CreditsResponse>

        @GET("movie/{movie_id}/reviews")
        fun getMovieReviews(@Path("movie_id") id: Int, @Query("page") page: Int): Observable<PaginationResult<Review>>

        @GET("movie/{movie_id}/similar")
        suspend fun getMovieSimilar(@Path("movie_id") id: Long, @Query("page") page: Int): Response<PaginationResult<DiscoverMovie>>

        @GET("movie/popular")
        fun getPopularMovies(@Query("page") page: Int): Observable<PaginationResult<Movie>>

        @GET("movie/upcoming")
        fun getUpcomingMovies(@Query("page") page: Int): Observable<PaginationResult<Movie>>

        @GET("movie/now_playing")
        fun getNowPlayingMovies(@Query("page") page: Int): Observable<PaginationResult<Movie>>

        @GET("movie/top_rated")
        fun getTopRatedMovies(@Query("page") page: Int): Observable<PaginationResult<Movie>>

        @GET("movie/latest")
        fun getLatestMovies(@Query("page") page: Int): Observable<PaginationResult<Movie>>

        @GET("search/movie")
        fun getMoviesSearch(@Query("page") page: Int, @Query("query") query: String) : Observable<PaginationResult<DiscoverMovie>>

        @GET("movie/{movie_id}/account_states")
        fun getAccountMovie(@Path("movie_id") id: Int): Observable<AccountMovie>

        @POST("movie/{movie_id}/rating")
        fun postRateMovie(@Path("movie_id") movieId: Int, @Body rate: Rated): Observable<RatingResponse>
    }

    interface TokenAPI {

        @GET("authentication/token/new")
        suspend fun getToken(): Response<Token>

        @POST("authentication/token/validate_with_login")
        suspend fun loginToken(@Body userData: Auth): Response<Token>

        @POST("authentication/session/new")
        suspend fun getSession(@Body userData: RequestToken): Response<Session>
    }
}