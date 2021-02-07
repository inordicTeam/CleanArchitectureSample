package com.example.moviesfinder.presentation

import android.util.Log
import com.example.moviesfinder.domain.repositories.IMovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val movieRepository: IMovieRepository
) : MvpPresenter<MainView>(), CoroutineScope {
    override val coroutineContext = Dispatchers.Main

    fun onSpinClicked(isMovie: Boolean, isTVShow: Boolean, rating: Int) = launch {
        viewState.setLoading(true)
        try {
            movieRepository.getRandomMovie(rating, isMovie, isTVShow).let {
                viewState.showMovie(it)
            }
        } catch (e: Exception) {
            Log.w(javaClass.simpleName, "Error", e)
            viewState.showError()
        } finally {
            viewState.setLoading(false)
        }
    }
}