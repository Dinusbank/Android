package com.dinusbank.tumbuhin.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.dinusbank.tumbuhin.R
import com.dinusbank.tumbuhin.databinding.FragmentSplash3Binding

class SplashFragment3 : Fragment() {

    private lateinit var binding: FragmentSplash3Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplash3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext2.setOnClickListener {
            view.findNavController().navigate(R.id.action_splashFragment3_to_splashFragment4)
        }
    }
}