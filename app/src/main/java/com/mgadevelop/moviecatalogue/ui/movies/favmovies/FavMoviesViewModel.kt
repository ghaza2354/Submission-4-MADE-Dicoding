package com.mgadevelop.moviecatalogue.ui.movies.favmovies

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mgadevelop.moviecatalogue.db.MovieCatalogueDatabase
import com.mgadevelop.moviecatalogue.ui.movies.pojo.ResultsItem

class FavMoviesViewModel(application: Application) : AndroidViewModel(application) {
    private val favMovies = MutableLiveData<MutableList<ResultsItem>>()
    private val movieCatalogueDatabase: MovieCatalogueDatabase = MovieCatalogueDatabase.getDatabase(getApplication())


    val movies: MutableLiveData<MutableList<ResultsItem>>
        get() {
            return favMovies
        }

    fun fetchFavMovies(){
        AsyncTask.execute {
            favMovies.postValue(movieCatalogueDatabase.movieDao().getAllMovie())
        }
    }
}
