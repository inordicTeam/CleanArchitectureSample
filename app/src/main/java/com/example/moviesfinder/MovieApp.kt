package com.example.moviesfinder

import android.app.Application
import com.example.moviesfinder.dagger.AppComponent
import com.example.moviesfinder.dagger.DaggerAppComponent

class MovieApp : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}