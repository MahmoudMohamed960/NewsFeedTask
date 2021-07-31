package com.example.newsFeedsApp

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsFeedsApp.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.nav_drawer_layout.view.*


@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), DrawerMenuAdapter.OnItemClicked {
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: ActivityHomeBinding
    private var drawerAdapter: DrawerMenuAdapter? = null
    private val navController by lazy {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bind activity layout
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.label == "article") {
                binding.navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_left)
            } else {
                supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white)
                binding.navDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            }
        }
        setupToolbar()
        setDataToMenuDrawerList()
        setupNavDrawer()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.searchView.layoutParams = Toolbar.LayoutParams(Gravity.END)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_white)
            setTitle(R.string.link_text)
        }
    }

    private fun setDataToMenuDrawerList() {
        val items = homeViewModel.getNavDrawerList()
        drawerAdapter = DrawerMenuAdapter(this, items, this)
        binding.navView.nav_drawer_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.navView.nav_drawer_list.adapter = drawerAdapter
    }

    private fun setupNavDrawer() {
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.navDrawer)
    }

    override fun onBackPressed() {
        if (binding.navDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.navDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun OnItemSelected(title: String) {
        Toast.makeText(this, title, Toast.LENGTH_LONG).show()
    }
}