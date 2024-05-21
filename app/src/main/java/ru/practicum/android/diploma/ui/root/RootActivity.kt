package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private val binding: ActivityRootBinding by lazy { ActivityRootBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvRootConteiner) as NavHostFragment
        val navController = navHostFragment.navController

        // Toolbar
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolBarController(destination)
        }

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.btnFilter.setOnClickListener {
            navController.navigate(R.id.action_searchFragment_to_filtrationFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun toolBarController(destination: NavDestination) {
        when (destination.id) {
            R.id.searchFragment -> {
                binding.toolbar.isVisible = true
                binding.toolbar.navigationIcon = null
                binding.btnFilter.isVisible = true
                binding.btnFavorite.isVisible = false
                binding.btnShare.isVisible = false
            }

            R.id.filtrationFragment -> {
                binding.toolbar.isVisible = true
                binding.btnFavorite.isVisible = false
                binding.btnShare.isVisible = false
                binding.toolbar.setNavigationOnClickListener {
                    this.onBackPressedDispatcher.onBackPressed()
                }
                binding.btnFilter.isVisible = false
            }

            R.id.vacanciesFragment -> {
                binding.toolbar.isVisible = true
                binding.btnFavorite.isVisible = true
                binding.btnShare.isVisible = true
                binding.toolbar.setNavigationOnClickListener {
                    this.onBackPressedDispatcher.onBackPressed()
                }
                binding.btnFilter.isVisible = false
            }

            R.id.favoriteFragment -> {
                binding.toolbar.isVisible = true
                binding.toolbar.navigationIcon = null
                binding.btnFilter.isVisible = false
                binding.btnFavorite.isVisible = false
                binding.btnShare.isVisible = false
            }

            R.id.teamFragment -> {
                binding.toolbar.isVisible = true
                binding.toolbar.navigationIcon = null
                binding.btnFilter.isVisible = false
                binding.btnFavorite.isVisible = false
                binding.btnShare.isVisible = false
            }
        }
    }

}
