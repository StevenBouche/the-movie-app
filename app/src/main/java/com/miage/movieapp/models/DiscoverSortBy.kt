package com.miage.movieapp.models

import com.miage.movieapp.R

enum class DiscoverSortBy(val id: Long, val value: String, val titleRes: Int) {
    POPULARITY_DESC(1,"popularity.desc", R.string.popularity_desc),
    POPULARITY_ASC(2,"popularity.asc", R.string.popularity_asc),
    RELEASE_DATE_DESC(3,"release_date.desc", R.string.release_date_desc),
    RELEASE_DATE_ASC(4,"release_date.asc", R.string.release_date_asc),
    ORIGINAL_TITLE_DESC(5,"original_title.desc", R.string.original_title_desc),
    ORIGINAL_TITLE_ASC(6,"original_title.asc", R.string.original_title_asc),
    VOTE_AVERAGE_DESC(7,"vote_average.desc", R.string.vote_average_desc),
    VOTE_AVERAGE_ASC(8,"vote_average.asc", R.string.vote_average_asc),
    VOTE_COUNT_DESC(9,"vote_count.desc", R.string.vote_count_desc),
    VOTE_COUNT_ASC(10,"vote_count.asc", R.string.vote_count_asc);
}