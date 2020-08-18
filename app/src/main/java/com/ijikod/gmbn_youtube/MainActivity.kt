package com.ijikod.gmbn_youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_holder) as NavHostFragment
    }

    private val appBarConfiguration : AppBarConfiguration by lazy {
        AppBarConfiguration(navHostFragment.navController.graph)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.fragmentFactory = VideoFragmentFactory()
        // setup toolbar with navigation component
        toolbar.setupWithNavController(navHostFragment.navController, appBarConfiguration)
    }
}