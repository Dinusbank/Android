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

        val sharedpreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        if (!sharedpreferences.getBoolean(prevStarted, false)) {
            val editor = sharedpreferences.edit()
            editor.putBoolean(prevStarted, true)
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