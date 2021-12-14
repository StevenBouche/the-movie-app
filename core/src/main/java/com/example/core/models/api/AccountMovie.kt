package com.example.core.models.api

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class AccountMovie (
    @SerializedName("id"        ) var id        : Int?     = null,
    @SerializedName("favorite"  ) var favorite  : Boolean? = null,
    @SerializedName("rated"     ) var rated     : Any?   = null,
    @SerializedName("watchlist" ) var watchlist : Boolean? = null
) {
    fun accountRated(): Rated? {
        try {
            if(rated is LinkedTreeMap<*, *>){
                val item = (rated as LinkedTreeMap<*, *>)["value"] as Double
                return Rated(item.toFloat())
            }
        } catch (e: Exception ){
            return Rated(0f)
        }
        return null
    }
}