package com.miage.movieapp.ui.authentification

import com.example.core.models.api.Auth
import com.example.core.repository.TokenRepository
import com.miage.movieapp.ui.fragment.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(
    private val repo: TokenRepository
) : BaseViewModel() {

    private val auth: Auth = Auth()

    var username: String?
        get() = auth.username
        set(value) { auth.username = value }

    var password: String?
        get() = auth.password
        set(value) { auth.password = value }

    suspend fun tryAuth(callback: (Boolean) -> Unit){
        withContext(Dispatchers.IO){
            callback(repo.authenticate(auth))
        }
    }
}