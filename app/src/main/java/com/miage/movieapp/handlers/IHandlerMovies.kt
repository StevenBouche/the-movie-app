package com.miage.movieapp.handlers

import com.miage.movieapp.adapters.models.MovieView

interface IHandlerMovies {
    fun onClick(item: MovieView)
}