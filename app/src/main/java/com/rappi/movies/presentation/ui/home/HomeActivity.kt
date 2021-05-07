package com.rappi.movies.presentation.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rappi.movies.R
import com.rappi.movies.databinding.ActivityHomeBinding
import com.rappi.movies.presentation.ui.search.SearchMoviesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
        this.binding.toolbar.title = ""
        setSupportActionBar(this.binding.toolbar)
        this.initTabAdapter()
    }

    private fun initTabAdapter() {

        // Tabs Customization
        this.binding.contentHome.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
        this.binding.contentHome.tabLayout.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.blue_tmdb
            )
        )
        val numberOfTabs = 2

        this.binding.contentHome.apply {
            tabLayout.tabTextColors =
                ContextCompat.getColorStateList(this@HomeActivity, R.color.color_acent)
            tabLayout.tabMode = TabLayout.MODE_FIXED
            tabLayout.isInlineLabel = true
            tabsViewpager.adapter = TabsPagerAdapter(this@HomeActivity, numberOfTabs)
            tabsViewpager.isUserInputEnabled = true
        }

        // Link the TabLayout and the ViewPager2 together and Set Text & Icons
        TabLayoutMediator(
            this.binding.contentHome.tabLayout,
            this.binding.contentHome.tabsViewpager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Popular"
                    tab.setIcon(R.drawable.ic_star)
                }
                1 -> {
                    tab.text = "Top Rated"
                    tab.setIcon(R.drawable.ic_favorite)
                }

            }
            // Change color of the icons
            tab.icon?.colorFilter =
                BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    Color.WHITE,
                    BlendModeCompat.SRC_ATOP
                )
        }.attach()


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle toolbar item clicks here. It'll
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_search -> {
                // Open the search view on the menu item click.
                startActivity(Intent(this, SearchMoviesActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}