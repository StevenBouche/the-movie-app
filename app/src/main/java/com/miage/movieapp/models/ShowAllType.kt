package com.miage.movieapp.models

import com.miage.movieapp.R

enum class ShowAllType(val nameRes: Int) {
    THEATER(R.string.in_theaters),
    POPULAR(R.string.popular),
    UPCOMING(R.string.upcoming),
    TOP_RATED(R.string.top_rated)
}