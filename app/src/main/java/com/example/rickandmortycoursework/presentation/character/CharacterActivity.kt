package com.example.rickandmortycoursework.presentation.character

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.rickandmortycoursework.R
import com.example.rickandmortycoursework.databinding.ActivityCharacterBinding
import com.example.rickandmortycoursework.util.CalculateWindowSize
import com.example.rickandmortycoursework.util.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCharacterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setTheme(R.style.Theme_RickyAndMorty)
        binding = ActivityCharacterBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val heightWindowClasses = CalculateWindowSize(this).calculateCurrentHeightSize()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHostFragment.navController

        binding.bottomNavigationBar?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val isVisibleBottomBar = when (destination.id) {
                R.id.locationDetailFragment -> false
                R.id.episodeDetailFragment -> false
                R.id.characterDetailFragment -> false

                else -> true
            }

            binding.bottomNavigationBar?.isVisible = isVisibleBottomBar
        }

        binding.navigationRail?.apply {
            setupWithNavController(navController)

            if (heightWindowClasses == WindowSizeClass.MEDIUM || heightWindowClasses == WindowSizeClass.EXPANDED) {
                addHeaderView(R.layout.navigation_rail_header)

                headerView?.setOnClickListener {
                    if (navController.currentDestination?.displayName == NavDestination.getDisplayName(
                            this@CharacterActivity,
                            R.id.characterListFragment
                        )
                    ) {
                        return@setOnClickListener
                    } else {
                        navController.navigate(R.id.characterListFragment)
                    }

                }
            }


        }
    }

}
