package com.example.moviesfinder.dagger

import android.app.Application
import com.example.moviesfinder.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DataModule::class, RepositoryModule::class])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(mainActivity: MainActivity)
}