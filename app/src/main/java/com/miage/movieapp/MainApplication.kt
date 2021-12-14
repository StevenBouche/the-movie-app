package com.miage.movieapp

import android.app.Application
import com.example.core.di.dataModule
import com.miage.movieapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Point d'entrée de l'application
 * C'est la première classe (d'une application Android) qui s'exécute au lancement de l'application
 * Elle peut être considérée comme un singleton, cad il n'existe qu'une instance non nulle de cette classe
 * pendant toute la durée de vie de l'application
 */
class MainApplication : Application() {

    /**
     * Méthode qui s'exécute à la créatioo de l'application
     * On en profite pour injecter tous les modules Koin
     */
    override fun onCreate() {
        super.onCreate()
        // Au démarrage de l'application, on indique à Koin, les différents modules à injecter
        startKoin {
            androidContext(this@MainApplication)
            //Ici on ajoute que deux modules, d'autre pourront être ajoutés au besoin
            modules(appModule + dataModule)
        }
    }

}