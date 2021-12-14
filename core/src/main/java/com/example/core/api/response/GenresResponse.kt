package com.example.core.api.response

import com.example.core.models.api.Genre
import com.google.gson.annotations.SerializedName

data class GenresResponse(
    @SerializedName("genres") val genres: List<Genre>
)