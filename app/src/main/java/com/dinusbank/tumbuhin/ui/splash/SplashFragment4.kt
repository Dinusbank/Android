package com.dinusbank.tumbuhin.ui.splash

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import com.dinusbank.tumbuhin.R

class SplashFragment4 : Fragment() {

    companion object{
        const val SET_BOOLEAN = "set_boolean"
        const val PREFERENCE = "preference"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_splash4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNext3 = activity?.findViewById<ImageView>(R.id.btn_next3)
        val sharedPreferences = context?.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        btnNext3?.setOnClickListener {
            view.findNavController().navigate(R.id.action_splashFragment4_to_mainActivity)

            editor?.putBoolean(SET_BOOLEAN, false)
            editor?.apply()
        }
    }
}