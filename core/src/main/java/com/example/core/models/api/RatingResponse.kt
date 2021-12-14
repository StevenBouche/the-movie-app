package com.example.core.models.api

import com.google.gson.annotations.SerializedName

data class RatingResponse(
    @SerializedName("status_code")
    val code: Int? = null,
    @SerializedName("status_message")
    val message: String? = null
)