package com.example.core.repository

import com.example.core.models.entity.TokenEntity
import com.example.core.api.service.TheMovieAPI
import com.example.core.local.daos.SessionDao
import com.example.core.local.daos.TokenDao
import com.example.core.models.api.Auth
import com.example.core.models.api.RequestToken
import com.example.core.models.api.Session
import com.example.core.models.api.Token
import com.example.core.models.entity.SessionEntity
import com.example.core.utils.ResultApi
import com.example.core.utils.parse
import com.example.core.utils.safeCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TokenRepository(
    private val serviceAuth: TheMovieAPI.TokenAPI
) : KoinComponent {

    private val tokenDao: TokenDao by inject()
    private val sessionDao: SessionDao by inject()

    private suspend fun generateToken(): ResultApi<Token> {
        return safeCall {
            val response = serviceAuth.getToken()
            response.parse()
        }
    }

    private suspend fun generateValidation(auth: Auth): ResultApi<Token> {
        return safeCall {
            val response = serviceAuth.loginToken(auth)
            response.parse()
        }
    }

    private suspend fun generateSession(auth: RequestToken): ResultApi<Session> {
        return safeCall {
            val response = serviceAuth.getSession(auth)
            response.parse()
        }
    }

    private suspend fun getSession(token : RequestToken): Boolean {
        return when(val session = generateSession(token)){
            is ResultApi.Success -> {
                session.data.sessionId?.let {
                    sessionDao.insert(SessionEntity(it))
                }
                return true
            }
            is ResultApi.Error -> false
        }
    }

    private suspend fun getValidation(auth: Auth): Boolean{
        return when(val validate = generateValidation(auth)){
            is ResultApi.Success -> {
                tokenDao.insert(TokenEntity.fromApi(validate.data))
                return getSession(RequestToken(auth.token))
            }
            is ResultApi.Error -> false
        }
    }

    suspend fun authenticate(auth: Auth): Boolean {
        return when(val result = generateToken()) {
            is ResultApi.Success -> {
                auth.token = result.data.requestToken
                return getValidation(auth)
            }
            is ResultApi.Error -> false
        }
    }
}