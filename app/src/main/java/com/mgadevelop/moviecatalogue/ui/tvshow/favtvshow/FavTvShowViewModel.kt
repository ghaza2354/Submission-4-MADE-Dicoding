package com.mgadevelop.moviecatalogue.ui.tvshow.favtvshow

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mgadevelop.moviecatalogue.db.MovieCatalogueDatabase
import com.mgadevelop.moviecatalogue.ui.tvshow.pojo.ResultsItem

class FavTvShowViewModel(application: Application) : AndroidViewModel(application) {
    private val favTvShow = MutableLiveData<MutableList<ResultsItem>>()
    private val movieCatalogueDatabase: MovieCatalogueDatabase = MovieCatalogueDatabase.getDatabase(getApplication())

    internal val getTvShowList: MutableLiveData<MutableList<ResultsItem>>
        get() {
            return favTvShow
        }

    internal fun fetchFavTvShows() {
        AsyncTask.execute {
            favTvShow.postValue(movieCatalogueDatabase.tvShowDao().getAllTvShow())
        }
    }
}
