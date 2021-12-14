package com.example.core.interceptor

import com.example.core.local.daos.SessionDao
import com.example.core.local.daos.TokenDao
import okhttp3.Interceptor
import java.util.*

/**
 * Intercepteur qui modifie l'entête de chaque requête
 */
class BasicInterceptor(
    apiKey: String,
    private val dao: SessionDao
) {
    val requestInterceptor = Interceptor { chain ->
        val original = chain.request()

        val language = Locale.getDefault().language
        val urlBuilder = original.url
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .addQueryParameter("language", language)

        dao.retrieve()?.let {
            urlBuilder.addQueryParameter("session_id", it.id)
        }

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            .addHeader("Accept-Language", language)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .url(urlBuilder.build())

        val request = requestBuilder.build()
        return@Interceptor chain.proceed(request)
    }
}