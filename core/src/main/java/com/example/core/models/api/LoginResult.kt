package com.example.core.models.api

import com.google.gson.annotations.SerializedName

data class LoginResult(
    @SerializedName("success"         ) var success        : Boolean?        = null,
    @SerializedName("request_token" ) var token : String?        = null,
)