package com.example.core.config

import com.example.core.interceptor.BasicInterceptor
import com.example.core.interceptor.TokenInterceptor
import com.example.core.local.daos.SessionDao
import com.example.core.local.daos.TokenDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import java.util.concurrent.TimeUnit

object NetworkXConfig {
    fun buildHttpClient(apiKey: String, dao: TokenDao, sessionDao: SessionDao): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(BasicInterceptor(apiKey, sessionDao).requestInterceptor)
            .addInterceptor(TokenInterceptor(dao).requestInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()

    }
}