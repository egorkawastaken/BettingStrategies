package com.bettingstrategies.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bettingstrategies.R
import com.bettingstrategies.databinding.ActivityMainBinding
import com.bettingstrategies.presentation.fragments.NavigationHost
import com.bettingstrategies.presentation.fragments.strategies.StrategiesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavigationHost {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var navController: NavController? = null
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModel<StrategiesViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHost.findNavController()
        appBarConfiguration = AppBarConfiguration(TOOLBAR_DESTINATION)

        val mainBottomNavigation = findViewById<BottomNavigationView>(R.id.main_bottom_navigation)
        mainBottomNavigation.setupWithNavController(navController!!)
        viewModel.downloadStrategies()
    }

    override fun onStop() {
        super.onStop()
        viewModel.deleteAll()

    }

    override fun setupToolbar(toolbar: Toolbar) {
        navController?.apply {
            toolbar.setupWithNavController(this, appBarConfiguration)
        }
    }

    companion object {
        private val TOOLBAR_DESTINATION = setOf(
            R.id.favouriteStrategies,
            R.id.bettingStrategies,
            R.id.strategyFragment

        )
    }

}