package com.example.core.utils

import retrofit2.Response
import java.io.IOException

class NoDataException: Exception()
class NetworkException: Exception()

fun <T : Any> Response<T>.parse(): ResultApi<T> {
    return if (isSuccessful) {
        body()?.let {
            ResultApi.Success(it)
        } ?: run {
            ResultApi.Error(
                exception = NoDataException(),
                message = "Aucune donnée",
                code = 404
            )
        }
    } else {
        ResultApi.Error(
            exception = Exception(),
            message = message(),
            code = code()
        )
    }
}

suspend fun <T : Any> safeCall(execute: suspend () -> ResultApi<T>): ResultApi<T> {
    return try {
        execute()
    } catch (e: Exception) {
        if (e is IOException) {
            ResultApi.Error(
                exception = NetworkException(),
                message = "Problème d'accès au réseau",
                code = -1
            )
        } else {
            ResultApi.Error(
                exception = e,
                message = e.message ?: "No message",
                code = -1
            )
        }
    }
}



