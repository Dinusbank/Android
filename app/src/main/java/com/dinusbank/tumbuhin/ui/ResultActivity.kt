package com.dinusbank.tumbuhin.ui

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dinusbank.tumbuhin.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        if (bundle != null){
            val setImage: Uri = Uri.parse(bundle.getString("imageId"))
            binding.ivLeafes.setImageURI(setImage)
        }

        binding.btnBack.setOnClickListener {
            super.onBackPressed()
        }
    }
}