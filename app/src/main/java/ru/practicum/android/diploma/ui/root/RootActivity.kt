package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
//import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var binding: ActivityRootBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_root)

        // Пример использования access token для HeadHunter API
//        networkRequestExample(accessToken = BuildConfig.HH_ACCESS_TOKEN)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fcvRootConteiner) as NavHostFragment
        val navController = navHostFragment.navController

        // Toolbar
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding?.toolbar?.setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.searchFragment -> {
                    binding?.toolbar?.isVisible = true
                    binding?.toolbar?.navigationIcon = null
                }

                R.id.filtrationFragment -> {
                    binding?.toolbar?.isVisible = true
                    binding?.toolbar?.setNavigationOnClickListener {
                        this.onBackPressedDispatcher.onBackPressed()
                    }
                }

                R.id.vacanciesFragment -> {
                    binding?.toolbar?.isVisible = true
                    binding?.toolbar?.setNavigationOnClickListener {
                        this.onBackPressedDispatcher.onBackPressed()
                    }
                }

                R.id.favoriteFragment -> {
                    binding?.toolbar?.isVisible = false
                    binding?.toolbar?.navigationIcon = null
                }

                R.id.teamFragment -> {
                    binding?.toolbar?.isVisible = false
                    binding?.toolbar?.navigationIcon = null
                }
            }
        }

        binding?.bottomNavigationView?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun networkRequestExample(accessToken: String) {
        // ...
    }
}
