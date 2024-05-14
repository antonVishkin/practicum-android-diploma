package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var binding: ActivityRootBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Пример использования access token для HeadHunter API
//        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)
        Log.v("TEST", "${BuildConfig.HH_ACCESS_TOKEN}")
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvRootConteiner) as NavHostFragment
        val navController = navHostFragment.navController

        // Toolbar
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding?.toolbar?.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            toolBarController(destination)
        }

        binding?.bottomNavigationView?.setupWithNavController(navController)

        val filterButton = findViewById<ImageButton>(R.id.filter_btn)
        filterButton?.setOnClickListener {
            // Переходим на фрагмент фильтрации
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
                binding?.toolbar?.isVisible = true
                binding?.toolbar?.navigationIcon = null
                binding?.filterBtn?.isVisible = true
            }

            R.id.filtrationFragment -> {
                binding?.toolbar?.isVisible = true
                binding?.toolbar?.setNavigationOnClickListener {
                    this.onBackPressedDispatcher.onBackPressed()
                }
                binding?.filterBtn?.isVisible = false
            }

            R.id.vacanciesFragment -> {
                binding?.toolbar?.isVisible = true
                binding?.toolbar?.setNavigationOnClickListener {
                    this.onBackPressedDispatcher.onBackPressed()
                }
                binding?.filterBtn?.isVisible = false
            }

            R.id.favoriteFragment -> {
                binding?.toolbar?.isVisible = true
                binding?.toolbar?.navigationIcon = null
                binding?.filterBtn?.isVisible = false
            }

            R.id.teamFragment -> {
                binding?.toolbar?.isVisible = true
                binding?.toolbar?.navigationIcon = null
                binding?.filterBtn?.isVisible = false
            }
        }
    }

}
