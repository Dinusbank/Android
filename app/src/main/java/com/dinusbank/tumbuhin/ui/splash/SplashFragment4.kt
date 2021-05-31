package com.dinusbank.tumbuhin.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.findNavController
import com.dinusbank.tumbuhin.R

class SplashFragment4 : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_splash4, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnNext3 = activity?.findViewById<ImageView>(R.id.btn_next3)

        btnNext3?.setOnClickListener {
            view.findNavController().navigate(R.id.action_splashFragment4_to_mainActivity)
        }
    }
}