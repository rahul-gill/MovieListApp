package com.github.rahul_gill.movieapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.github.rahul_gill.movieapp.AppNavGraphDirections
import com.github.rahul_gill.movieapp.R
import com.github.rahul_gill.movieapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var bootomNavigation: BottomNavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
//        binding.bottomNavigation.setupWithNavController(navController)
        binding.bottomNavigation.selectedItemId = R.id.mainScreen
        bootomNavigation = binding.bottomNavigation
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.searchScreen ->
                    navController.navigate(AppNavGraphDirections.toMainScreen(initiatedForSearch = true))
                R.id.mainScreen ->
                    navController.navigate(AppNavGraphDirections.toMainScreen(initiatedForSearch = false))
            }
            return@setOnItemSelectedListener true
        }
    }
}