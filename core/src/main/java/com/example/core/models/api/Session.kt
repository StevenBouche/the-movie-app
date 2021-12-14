package com.example.core.models.api

import com.google.gson.annotations.SerializedName

data class Session(
    @SerializedName("success"         ) var success        : Boolean?        = null,
    @SerializedName("session_id" ) var sessionId : String?        = null,
)