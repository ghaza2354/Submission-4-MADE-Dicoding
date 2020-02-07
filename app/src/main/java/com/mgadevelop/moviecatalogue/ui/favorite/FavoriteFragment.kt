package com.mgadevelop.moviecatalogue.ui.favorite

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.mgadevelop.moviecatalogue.R
import com.mgadevelop.moviecatalogue.databinding.FavoriteFragmentBinding


class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var binding: FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.favorite_fragment, container, false)
        favoriteViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        binding.viewmodel = favoriteViewModel
        binding.viewPager.adapter = SectionsPagerAdapter(binding.root.context, childFragmentManager)
        binding.tabs.setupWithViewPager(binding.viewPager)

        return binding.root
    }

}
