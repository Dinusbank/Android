@file:Suppress("DEPRECATION")

package com.dinusbank.tumbuhin.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dinusbank.tumbuhin.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var doubleBackExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.bottom_nav)
        val navController = findNavController(R.id.nav_host_fragment)

        navView.setupWithNavController(navController)
        navView.itemIconTintList = null

    }

    override fun onBackPressed() {
        if (doubleBackExit){
            finishAffinity()
            return
        }

        this.doubleBackExit = true
        Toast.makeText(this, "Klik dua kali untuk exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(
            { doubleBackExit = false }, 2000)
    }
}