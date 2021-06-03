package com.dinusbank.tumbuhin.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinusbank.tumbuhin.R
import com.dinusbank.tumbuhin.ui.main.MainActivity


class MainSplashActivity : AppCompatActivity() {

    private var prevStarted = "yes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainsplash)

    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        if (sharedPreferences.getBoolean(prevStarted, true)) {
            val editor = sharedPreferences.edit()
            editor.putBoolean(prevStarted, false)
            editor.apply()
        } else {
            moveToSecondary()
        }
    }

    private fun moveToSecondary() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}