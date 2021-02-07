package com.example.moviesfinder.presentation

import com.example.moviesfinder.domain.entities.Movie
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface MainView : MvpView {
    @AddToEndSingle fun showMovie(movie: Movie)
    @AddToEndSingle fun setLoading(isLoading: Boolean)
    @OneExecution fun showError()
}