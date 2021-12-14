package com.miage.movieapp.adapters.models

import com.example.core.models.api.DiscoverMovie
import com.example.core.models.api.Movie
import com.example.core.models.entity.MovieFavoriteEntity

data class MovieView (
    val id: Int,
    val title: String,
    val posterPath: String?
){
    constructor(movie: DiscoverMovie) : this(movie.id, movie.title, movie.posterPath)
    constructor(movie: MovieFavoriteEntity) : this(movie.id, movie.title, movie.posterPath)
    constructor(movie: Movie) : this(movie.id, movie.title, movie.posterPath)
}