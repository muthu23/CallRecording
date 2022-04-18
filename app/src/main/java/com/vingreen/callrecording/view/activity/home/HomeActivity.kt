package com.vingreen.callrecording.view.activity.home

import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.vingreen.callrecording.R
import com.vingreen.callrecording.base.BaseActivity
import com.vingreen.callrecording.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding>(ActivityHomeBinding::inflate) {

    private lateinit var appBarConfiguration: AppBarConfiguration
    override val mLogTag: String get() = HomeActivity::class.toString()


    override fun setUp() {
        setSupportActionBar(binding.appBarHomeActivity.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_home_activity)
        val navController = navHostFragment?.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_leads, R.id.nav_profile
            ), drawerLayout
        )
        navController?.let {
            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home_activity)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}