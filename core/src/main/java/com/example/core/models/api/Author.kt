package com.example.core.models.api

import com.google.gson.annotations.SerializedName

data class Author  (
    @SerializedName("name"        ) var name       : String? = null,
    @SerializedName("username"    ) var username   : String? = null,
    @SerializedName("avatar_path" ) var avatarPath : String? = null,
    @SerializedName("rating"      ) var rating     : String? = null
)