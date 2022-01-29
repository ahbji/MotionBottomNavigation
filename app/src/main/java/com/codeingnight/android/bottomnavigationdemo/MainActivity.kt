package com.codeingnight.android.bottomnavigationdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.codeingnight.android.bottomnavigationdemo.databinding.*

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).navController
        val destinationMap = mapOf(
            R.id.messageFragment to binding.messageIconLayout.messageMotionLayout,
            R.id.contactFragment to binding.contactIconLayout.contactMotionLayout,
            R.id.exploreFragment to binding.exploreIconLayout.exploreMotionLayout,
            R.id.accountFragment to binding.accountIconLayout.accountMotionLayout
        )
        setupActionBarWithNavController(
            navController,
            AppBarConfiguration(
                destinationMap.keys
            )
        )

        destinationMap.forEach { map ->
            map.value.setOnClickListener { navController.navigate(map.key) }
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            controller.popBackStack()
            destinationMap.values.forEach { it.progress = 0.001f }
            destinationMap[destination.id]?.transitionToEnd()
        }
    }
}