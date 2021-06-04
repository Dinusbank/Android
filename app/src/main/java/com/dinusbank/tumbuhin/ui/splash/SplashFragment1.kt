package com.dinusbank.tumbuhin.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.dinusbank.tumbuhin.R
import com.dinusbank.tumbuhin.ui.main.MainActivity
import com.dinusbank.tumbuhin.ui.splash.SplashFragment4.Companion.setPreference

class SplashFragment1 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_splash1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnStart = activity?.findViewById<Button>(R.id.btn_start)

        if (setPreference) {
            btnStart?.setOnClickListener {
                view.findNavController().navigate(R.id.action_splashFragment1_to_splashFragment2)
            }
        } else {
            moveToMain()
        }
    }

    private fun moveToMain() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }
}