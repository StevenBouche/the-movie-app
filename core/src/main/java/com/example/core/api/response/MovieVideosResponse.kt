package com.example.core.api.response

import com.example.core.models.api.MovieVideo
import com.google.gson.annotations.SerializedName

data class MovieVideosResponse(
    @SerializedName("id"      ) var id      : Int?          = null,
    @SerializedName("results" ) var results : List<MovieVideo> = arrayListOf()
)