package com.miage.movieapp.handlers

import com.example.core.models.api.Movie

interface IHandlerHome {
    fun onClickMovie(item: Movie)
}