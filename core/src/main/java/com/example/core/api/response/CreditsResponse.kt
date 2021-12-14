package com.example.core.api.response

import com.example.core.models.api.Cast
import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("cast")
    var results: List<Cast>
)