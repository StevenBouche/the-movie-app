package com.example.core.models.api

import com.google.gson.annotations.SerializedName

data class Auth (
    @SerializedName("username" ) var username : String? = null,
    @SerializedName("password" ) var password : String? = null,
    @SerializedName("request_token" ) var token : String? = null,
)