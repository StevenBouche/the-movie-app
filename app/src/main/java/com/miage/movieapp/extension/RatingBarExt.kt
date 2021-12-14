package com.miage.movieapp.extension

import android.widget.RatingBar
import com.example.core.api.service.TheMovieAPI
import com.example.core.models.api.Movie

fun RatingBar.setRatingBar(movie: Movie?, stars: Int) {
    movie?.let {
        this.rating = (stars * (it.voteAverage / TheMovieAPI.MAX_RATING))
    }
}

fun RatingBar.setRatingBar(value: Float?, stars: Int) {
    value?.let {
        this.rating = (stars * (value / TheMovieAPI.MAX_RATING))
    }
}
