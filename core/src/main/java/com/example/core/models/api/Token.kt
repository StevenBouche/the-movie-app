package com.example.core.models.api

import com.google.gson.annotations.SerializedName

/**
 * Classe modélisant la réponse du serveur lors de la demande d'un Token
 * Cette classe est uniquement utilisée pour récupérer la réponse du serveur
 * Les instances de cette classe ne sont jamais exposées aux autres composants
 */
data class Token(
    @SerializedName("expires_at")
    val expiresAt: String? = null,
    @SerializedName("request_token")
    val requestToken: String? = null,
    @SerializedName("success")
    val success: Boolean? = null
)

