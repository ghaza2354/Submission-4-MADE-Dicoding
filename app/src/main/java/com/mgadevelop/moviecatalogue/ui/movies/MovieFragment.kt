package com.mgadevelop.moviecatalogue.ui.movies


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
import com.mgadevelop.moviecatalogue.databinding.FragmentMovieBinding
import com.mgadevelop.moviecatalogue.ui.movies.detailmovie.DetailMovieActivity
import com.mgadevelop.moviecatalogue.ui.movies.pojo.ResponseMovies
import com.mgadevelop.moviecatalogue.ui.movies.pojo.ResultsItem


class MovieFragment : Fragment() {

    lateinit var alertDialog: AlertDialog
    lateinit var binding: FragmentMovieBinding
    lateinit var mViewModel: MovieFragmentViewModel


    private val getMovies = Observer<ResponseMovies> { responseMovies ->
        if (responseMovies != null) {
            if (responseMovies.anError == null) {
                val mAdapter = MoviesAdapter(responseMovies.results!!)

                mAdapter.SetOnItemClickListener(object : MoviesAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, model: ResultsItem) {
                        val goToDetailMovie = Intent(view.context, DetailMovieActivity::class.java)
                        goToDetailMovie.putExtra(DetailMovieActivity.SELECTED_MOVIE, model)
                        startActivity(goToDetailMovie)
                    }
                })

                binding.recyclerView.adapter = mAdapter
            } else {
                alertDialog.setMessage(responseMovies.anError.message)
                alertDialog.show()
            }
        }
        binding.progressBar.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_movie,container,false)
        mViewModel = ViewModelProviders.of(this).get(MovieFragmentViewModel::class.java)
        binding.viewmodel = mViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertDialog = AlertDialog.Builder(view.context).setTitle(getString(R.string.failure)).setPositiveButton("OK") { dialog, which -> }.create()

        val layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.doRequestListMovies()
        mViewModel.movies.observe(this, getMovies)
    }
}