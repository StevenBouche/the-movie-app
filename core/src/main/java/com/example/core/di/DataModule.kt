package com.example.core.di
import com.example.core.api.service.TheMovieAPI
import com.example.core.config.DatabaseConfig
import com.example.core.config.NetworkXConfig
import com.example.core.repository.DiscoverRepository
import com.example.core.repository.GenreRepository
import com.example.core.repository.MovieRepository
import com.example.core.repository.TokenRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single {
        NetworkXConfig.buildHttpClient(
            apiKey = get(named("API_KEY")),
            dao = get(),
            sessionDao = get()
        )
    }

    single() {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(get(named("BASE_URL")) as String)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
            .create(TheMovieAPI.GenresAPI::class.java)
    }

    single() {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(get(named("BASE_URL")) as String)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
            .create(TheMovieAPI.TokenAPI::class.java)
    }

    single() {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(get(named("BASE_URL")) as String)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
            .create(TheMovieAPI.MoviesAPI::class.java)
    }

    single() {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(get(named("BASE_URL")) as String)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(get())
            .build()
            .create(TheMovieAPI.DiscoverAPI::class.java)
    }

    single {
        DiscoverRepository(get())
    }

    single {
        GenreRepository(get())
    }

    single {
        MovieRepository(get())
    }

    single {
        TokenRepository(get())
    }

    single {
        DatabaseConfig.buildDatabase(get())
    }

    single {
        DatabaseConfig.getTokenDao(get())
    }

    single {
        DatabaseConfig.getMovieFavoriteDao(get())
    }

    single {
        DatabaseConfig.getSessionDao(get())
    }
}








