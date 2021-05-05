package com.rappi.movies.presentation.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabsPagerAdapter(activity: AppCompatActivity, private var numberOfTabs: Int) :
    FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // # popular Fragment
                val bundle = Bundle()
                bundle.putString("id_movies", "popular")
                val musicFragment = MoviesFragment()
                musicFragment.arguments = bundle
                return musicFragment
            }
            1 -> {
                // # top rated Fragment
                val bundle = Bundle()
                bundle.putString("id_movies", "top_rated")
                val moviesFragment = MoviesFragment()
                moviesFragment.arguments = bundle
                return moviesFragment
            }
            else -> return MoviesFragment()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}