package com.example.core.models.api

import com.google.gson.annotations.SerializedName

open class PaginationResult<T>(
    @SerializedName("page"          ) var page         : Int?                   = null,
    @SerializedName("results"       ) var results      : List<T>                = arrayListOf(),
    @SerializedName("total_results" ) var totalResults : Int?                   = null,
    @SerializedName("total_pages"   ) var totalPages   : Int?                   = null
)