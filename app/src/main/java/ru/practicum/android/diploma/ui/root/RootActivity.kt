package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private val binding: ActivityRootBinding by lazy { ActivityRootBinding.inflate(layoutInflater) }
    var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvRootConteiner) as NavHostFragment
        navController = navHostFragment.navController

        // Toolbar
        val appBarConfiguration = AppBarConfiguration(navController!!.graph)
        binding.toolbar.setupWithNavController(navController!!, appBarConfiguration)
        navController?.addOnDestinationChangedListener { _, destination, _ ->
            toolBarController(destination)
        }

        binding.bottomNavigationView.setupWithNavController(navController!!)

        binding.btnFilter.setOnClickListener {
            navController!!.navigate(R.id.action_searchFragment_to_filtrationFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun toolBarController(destination: NavDestination) {
        when (destination.id) {
            R.id.searchFragment -> {
                searchFragmentShown()
            }

            R.id.filtrationFragment -> {
                filtrationFragmentShown()
            }

            R.id.vacanciesFragment -> {
                vacanciesFragmentShown()
            }

            R.id.favoriteFragment -> {
                favoriteFragmentShown()
            }

            R.id.teamFragment -> {
                teamFragmentShown()
            }
        }
    }

    private fun searchFragmentShown() {
        binding.toolbar.visibility = View.VISIBLE
        binding.toolbar.navigationIcon = null
        binding.btnFilter.isVisible = true
        binding.bottomNavigationView.isVisible = true

    }

    private fun filtrationFragmentShown() {
        binding.toolbar.visibility = View.VISIBLE
        binding.toolbar.setNavigationOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
        binding.btnFilter.isVisible = false
        binding.bottomNavigationView.isVisible = true
    }

    private fun vacanciesFragmentShown() {
        binding.toolbar.visibility = View.GONE
        binding.toolbar.setNavigationOnClickListener {
            this.onBackPressedDispatcher.onBackPressed()
        }
        binding.btnFilter.isVisible = false
        binding.bottomNavigationView.isVisible = false
    }

    private fun favoriteFragmentShown() {
        binding.toolbar.visibility = View.VISIBLE
        binding.toolbar.navigationIcon = null
        binding.btnFilter.isVisible = false
        binding.bottomNavigationView.isVisible = true

    }

    private fun teamFragmentShown() {
        binding.toolbar.visibility = View.VISIBLE
        binding.toolbar.navigationIcon = null
        binding.btnFilter.isVisible = false
        binding.bottomNavigationView.isVisible = true

    }

}
