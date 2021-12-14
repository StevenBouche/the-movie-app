package com.miage.movieapp.di

import android.content.Context
import com.miage.movieapp.ui.authentification.AuthViewModel
import com.miage.movieapp.ui.favorite.FavoriteViewModel
import com.miage.movieapp.ui.genres.GenresViewModel
import com.miage.movieapp.ui.home.HomeViewModel
import com.miage.movieapp.ui.moviedetails.MovieDetailsViewModel
import com.miage.movieapp.ui.moviereviews.MovieReviewsViewModel
import com.miage.movieapp.ui.movies.MoviesViewModel
import com.miage.movieapp.ui.search.SearchViewModel
import com.miage.movieapp.ui.showall.ShowAllViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(named("API_KEY")) {
        "b57d89abe48166137e632d52a72dbf6d"
    }

    single(named("BASE_URL")) {
        "https://api.themoviedb.org/3/"
    }

    single(named("APP_PREFS")) {
        androidContext().getSharedPreferences("app_private", Context.MODE_PRIVATE)
    }

    viewModel {
        GenresViewModel(
            repository = get()
        )
    }

    viewModel {
        MoviesViewModel(
            repository = get()
        )
    }

    viewModel {
        MovieDetailsViewModel(
            repository = get()
        )
    }

    viewModel {
        HomeViewModel(
            repository = get()
        )
    }

    viewModel {
        MovieReviewsViewModel(
            repository = get()
        )
    }

    viewModel {
        SearchViewModel(
            repository = get()
        )
    }

    viewModel {
        FavoriteViewModel(repository = get())
    }

    viewModel {
        ShowAllViewModel(repository = get())
    }

    viewModel {
        AuthViewModel(repo = get())
    }
}