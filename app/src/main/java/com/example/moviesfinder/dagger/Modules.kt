package com.example.moviesfinder.dagger

import android.app.Application
import com.example.moviesfinder.data.api.MovieApi
import com.example.moviesfinder.data.repositories.MovieRepository
import com.example.moviesfinder.domain.repositories.IMovieRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [UtilModule::class])
class DataModule {

    @Provides
    @Singleton
    fun provideMoviesApi(gson: Gson): MovieApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(MovieApi.BASE_URL)
        .build()
        .create(MovieApi::class.java)

    @Provides
    fun provideDb(application: Application): String {
        // some stub method that uses external dependency
        return "foo"
    }
}

@Module
class UtilModule {
    @Provides
    fun provideGson(): Gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd")
        .create()
}

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindIMovieRepository(movieRepository: MovieRepository): IMovieRepository
}