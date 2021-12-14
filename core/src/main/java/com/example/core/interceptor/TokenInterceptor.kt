package com.example.core.interceptor

import com.example.core.local.daos.TokenDao
import okhttp3.Interceptor

/**
 * Un intercepteur qui modifie toutes les requêtes HTTP en y ajoutant un token
 * Le token est récupéré dans la BDD
 */
class TokenInterceptor(
    private val dao: TokenDao
) {
    val requestInterceptor = Interceptor { chain ->
        val original = chain.request()
        val originalHttpUrl = original.url
        val requestBuilder = original.newBuilder().url(originalHttpUrl.newBuilder().build())

        dao.retrieve()?.token?.let {
            requestBuilder.addHeader("Authorization", it)
        }
        val request = requestBuilder.build()
        return@Interceptor chain.proceed(request)
    }
}