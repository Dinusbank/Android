package com.dinusbank.tumbuhin.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.dinusbank.tumbuhin.R
import com.dinusbank.tumbuhin.databinding.FragmentSplash4Binding

class SplashFragment4 : Fragment() {

    private lateinit var binding: FragmentSplash4Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplash4Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext3.setOnClickListener {
            view.findNavController().navigate(R.id.action_splashFragment4_to_mainActivity)
        }
    }
}