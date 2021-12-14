package com.example.core.api.response

import com.example.core.models.api.Review
import com.google.gson.annotations.SerializedName

data class MovieReviewsResponse (
    @SerializedName("id"            ) var id           : Int?          = null,
    @SerializedName("page"          ) var page         : Int?          = null,
    @SerializedName("results"       ) var results      : List<Review> = arrayListOf(),
    @SerializedName("total_pages"   ) var totalPages   : Int?          = null,
    @SerializedName("total_results" ) var totalResults : Int?          = null
)