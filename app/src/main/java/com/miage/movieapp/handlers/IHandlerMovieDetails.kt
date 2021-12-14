package com.miage.movieapp.handlers

import com.example.core.models.api.MovieVideo

interface IHandlerMovieDetails {
    fun onClickVideo(item: MovieVideo)
}