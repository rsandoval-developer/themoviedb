package com.rappi.movies.presentation.ui.home

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rappi.movies.R
import com.rappi.movies.databinding.ActivityHomeBinding
import com.rappi.movies.presentation.ui.search.SearchMoviesActivity
import com.rappi.movies.presentation.ui.search.SearchMoviesViewModel
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
        this.binding.contentHome.tabLayout.tabTextColors =
            ContextCompat.getColorStateList(this, R.color.color_acent)

        // Set different Text Color for Tabs for when are selected or not
        //tab_layout.setTabTextColors(R.color.normalTabTextColor, R.color.selectedTabTextColor)

        // Number Of Tabs
        val numberOfTabs = 2

        // Set Tabs in the center
        //tab_layout.tabGravity = TabLayout.GRAVITY_CENTER

        // Show all Tabs in screen
        this.binding.contentHome.tabLayout.tabMode = TabLayout.MODE_FIXED

        // Scroll to see all Tabs
        //tab_layout.tabMode = TabLayout.MODE_SCROLLABLE

        // Set Tab icons next to the text, instead above the text
        this.binding.contentHome.tabLayout.isInlineLabel = true

        // Set the ViewPager Adapter
        val adapter = TabsPagerAdapter(this, numberOfTabs)
        this.binding.contentHome.tabsViewpager.adapter = adapter

        // Enable Swipe
        this.binding.contentHome.tabsViewpager.isUserInputEnabled = true


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