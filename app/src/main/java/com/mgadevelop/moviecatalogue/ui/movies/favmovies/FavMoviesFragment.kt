package com.mgadevelop.moviecatalogue.ui.movies.favmovies


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mgadevelop.moviecatalogue.R
import com.mgadevelop.moviecatalogue.databinding.FragmentFavMoviesBinding
import com.mgadevelop.moviecatalogue.ui.movies.MoviesAdapter
import com.mgadevelop.moviecatalogue.ui.movies.detailmovie.DetailMovieActivity
import com.mgadevelop.moviecatalogue.ui.movies.pojo.ResultsItem


class FavMoviesFragment : Fragment() {

    private lateinit var favMoviesFragmentBinding: FragmentFavMoviesBinding
    private lateinit var mViewModel: FavMoviesViewModel
    private lateinit var alertDialog: AlertDialog

    private val getFavMovie = Observer<List<ResultsItem>> {
        val mAdapter = MoviesAdapter(it)
        if (it.size > 0) {
            favMoviesFragmentBinding.tvMessage.visibility = View.GONE
            mAdapter.SetOnItemClickListener(object : MoviesAdapter.OnItemClickListener {
                override fun onItemClick(view: View, model: ResultsItem) {
                    val goToDetailMovie = Intent(view.context, DetailMovieActivity::class.java)
                    goToDetailMovie.putExtra(DetailMovieActivity.SELECTED_MOVIE, model)
                    startActivity(goToDetailMovie)
                }
            })

            favMoviesFragmentBinding.recyclerView.adapter = mAdapter
        } else {
            favMoviesFragmentBinding.recyclerView.adapter = null
            favMoviesFragmentBinding.tvMessage.visibility = View.VISIBLE
        }

        favMoviesFragmentBinding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        favMoviesFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav_movies, container, false)
        mViewModel = ViewModelProviders.of(this).get(FavMoviesViewModel::class.java)
        favMoviesFragmentBinding.viewmodel = mViewModel
        return favMoviesFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure)).setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        favMoviesFragmentBinding.recyclerView.layoutManager = layoutManager
        favMoviesFragmentBinding.recyclerView.setHasFixedSize(true)
        favMoviesFragmentBinding.viewmodel = mViewModel

    }

    override fun onResume() {
        super.onResume()
        mViewModel.fetchFavMovies()
        mViewModel.movies.observe(this, getFavMovie)
    }

}
