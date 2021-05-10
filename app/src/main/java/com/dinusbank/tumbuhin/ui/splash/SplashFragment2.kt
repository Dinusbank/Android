package com.dinusbank.tumbuhin.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.dinusbank.tumbuhin.R
import com.dinusbank.tumbuhin.databinding.FragmentSplash2Binding

class SplashFragment2 : Fragment() {

    private lateinit var binding: FragmentSplash2Binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplash2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnNext1.setOnClickListener {
            view.findNavController().navigate(R.id.action_splashFragment2_to_splashFragment3)
        }
    }
}